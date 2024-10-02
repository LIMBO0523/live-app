package org.live.user.provider.service.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.idea.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.live.common.interfaces.ConvertBeanUtils;
import org.live.user.constants.UserTagFieldNameConstants;
import org.live.user.constants.UserTagsEnum;
import org.live.user.dto.UserCacheAsyncDeleteDTO;
import org.live.user.dto.UserTagDTO;
import org.live.user.provider.dao.mapper.IUserTagMapper;
import org.live.user.provider.dao.po.UserTagPO;
import org.live.user.provider.service.IUserTagService;
import org.live.user.provider.utils.TimeUtils;
import org.live.user.utils.TagInfoUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.live.user.constants.UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 23:17
 */
@Service
public class UserTagServiceImpl implements IUserTagService {

    @Resource
    private IUserTagMapper userTagMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RedisTemplate<String, UserTagDTO> userTagDTORedisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private MQProducer mqProducer;

    @Override
    public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
        boolean updateStatus = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
        if (updateStatus) {
            deleteUserTagFromRedis(userId);
            return true;
        }
        String setNxKey = userProviderCacheKeyBuilder.buildUserTagLockKey(userId);
//        String setNxResult = redisTemplate.execute(new RedisCallback<String>() {
//
//            @Override
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
//                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
//                return (String) connection.execute("set", keySerializer.serialize(setNxKey),
//                        valueSerializer.serialize("-1"),
//                        "NX".getBytes(StandardCharsets.UTF_8),
//                        "EX".getBytes(StandardCharsets.UTF_8),
//                        "3".getBytes(StandardCharsets.UTF_8));
//            }
//        });
        RLock lock = redissonClient.getLock(setNxKey);
        boolean setNxResult;
        try {
            setNxResult = lock.tryLock(1L, 3L, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!setNxResult) {
            return false;
        }
        UserTagPO userTagPO = userTagMapper.selectById(userId);
        if (userTagPO != null) {
            return false;
        }
        userTagPO = new UserTagPO();
        userTagPO.setUserId(userId);
        userTagMapper.insert(userTagPO);
//        redisTemplate.delete(setNxResult);
        lock.unlock();
        return userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
    }

    @Override
    public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
        boolean cancelStatus = userTagMapper.cancelTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
        if (!cancelStatus) {
            return false;
        }
        deleteUserTagFromRedis(userId);
        return true;

    }

    @Override
    public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
        UserTagDTO userTagDTO = queryByUserIdFromRedis(userId);
        if (userTagDTO == null) {
            return false;
        }
        String queryFieldName = userTagsEnum.getFieldName();
        if (UserTagFieldNameConstants.TAG_INFO_01.equals(queryFieldName)) {
            return TagInfoUtils.isContain(userTagDTO.getTagInfo01(), userTagsEnum.getTag());
        } else if (UserTagFieldNameConstants.TAG_INFO_02.equals(queryFieldName)) {
            return TagInfoUtils.isContain(userTagDTO.getTagInfo02(), userTagsEnum.getTag());
        } else if (UserTagFieldNameConstants.TAG_INFO_03.equals(queryFieldName)) {
            return TagInfoUtils.isContain(userTagDTO.getTagInfo03(), userTagsEnum.getTag());
        }
        return false;
    }

    /**
     * 从Redis删除用户标签对象
     * @param userId
     */
    private void deleteUserTagFromRedis(Long userId) {
        String redisKey = userProviderCacheKeyBuilder.buildTagKey(userId);
        userTagDTORedisTemplate.delete(redisKey);
        UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = new UserCacheAsyncDeleteDTO();
        userCacheAsyncDeleteDTO.setCode(1);
        Map<String, Object> jsonParam = new HashMap<>();
        jsonParam.put("userId", userId);
        userCacheAsyncDeleteDTO.setJson(JSON.toJSONString(jsonParam));
        Message message = new Message();
        message.setBody(JSON.toJSONString(userCacheAsyncDeleteDTO).getBytes());
        message.setTopic(CACHE_ASYNC_DELETE_TOPIC);
        // 延迟级别，1代表延迟一秒钟发送
        message.setDelayTimeLevel(1);
        try {
            mqProducer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从Redis中查询用户标签对象
     * @param userId
     * @return
     */
    private UserTagDTO queryByUserIdFromRedis(Long userId) {
        String redisKey = userProviderCacheKeyBuilder.buildTagKey(userId);
        UserTagDTO userTagDTO = userTagDTORedisTemplate.opsForValue().get(redisKey);
        if (userTagDTO != null) {
            return userTagDTO;
        }
        UserTagPO userTagPO = userTagMapper.selectById(userId);
        if (userTagPO != null) {
            userTagDTO = ConvertBeanUtils.convert(userTagPO, UserTagDTO.class);
            userTagDTORedisTemplate.opsForValue().set(redisKey, userTagDTO, TimeUtils.createRandomExpireTime(60), TimeUnit.MINUTES);
            return userTagDTO;
        }
        return null;
    }
}

