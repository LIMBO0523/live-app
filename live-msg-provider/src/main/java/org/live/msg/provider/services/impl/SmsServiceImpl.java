package org.live.msg.provider.services.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomUtils;
import org.idea.live.framework.redis.starter.key.builder.MsgProviderCacheKeyBuilder;
import org.live.msg.dto.MsgCheckDTO;
import org.live.msg.emus.MsgSendResultEnum;
import org.live.msg.provider.config.ThreadPoolManager;
import org.live.msg.provider.dao.mapper.SmsMapper;
import org.live.msg.provider.dao.po.SmsPO;
import org.live.msg.provider.services.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 9:18
 */
@Service
public class SmsServiceImpl implements ISmsService {

    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Resource
    private SmsMapper smsMapper;
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    @Resource
    private MsgProviderCacheKeyBuilder redisKeyBuilder;

    @Override
    public MsgSendResultEnum sendMessage(String phone) {
        //模拟产生一条短信记录
        int code = RandomUtils.nextInt(100000, 999999);
        String key = redisKeyBuilder.buildSmsLoginCodeKey(phone);
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            logger.error("短期内同一个手机号不能发送太多次，phone is {}", phone);
            //短期内同一个手机号不能发送太多次
            return MsgSendResultEnum.SEND_FAIL;
        }
        redisTemplate.opsForValue().set(key, code);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        ThreadPoolManager.commonAsyncPool.execute(() -> {
            //模拟发送短信信息
            mockSendSms(phone, code);
            insertOne(phone, code);
        });
        return MsgSendResultEnum.SEND_SUCCESS;
    }

    @Override
    public MsgCheckDTO checkLoginCode(String phone, Integer code) {
        String key = redisKeyBuilder.buildSmsLoginCodeKey(phone);
        Integer recordCode = redisTemplate.opsForValue().get(key);
        if (recordCode == null) {
            return new MsgCheckDTO(false, "验证码已失效");
        }
        boolean isCodeVerify = recordCode.equals(code);
        if (isCodeVerify) {
            redisTemplate.delete(key);
            return new MsgCheckDTO(true, "");
        }
        return new MsgCheckDTO(false, "验证码校验失败");
    }

    @Override
    public void insertOne(String phone, Integer code) {
        SmsPO smsPO = new SmsPO();
        smsPO.setPhone(phone);
        smsPO.setCode(code);
        smsMapper.insert(smsPO);
    }

    /**
     * 模拟发送短信过程，感兴趣的朋友可以尝试对接一些第三方的短信平台
     *
     * @param phone
     * @param code
     */
    private void mockSendSms(String phone, Integer code) {
        try {
            logger.info(" ============= 创建短信发送通道中 ============= ,phone is {},code is {}", phone, code);
            Thread.sleep(1000);
            logger.info(" ============= 短信已经发送成功 ============= ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

