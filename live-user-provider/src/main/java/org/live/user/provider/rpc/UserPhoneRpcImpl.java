package org.live.user.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.idea.live.framework.redis.starter.key.builder.UserProviderCacheKeyBuilder;
import org.live.common.interfaces.utils.DESUtils;
import org.live.id.generate.enums.IdTypeEnum;
import org.live.id.generate.interfaces.IdBuilderRpc;
import org.live.user.dto.UserDTO;
import org.live.user.dto.UserLoginDTO;
import org.live.user.dto.UserPhoneDTO;
import org.live.user.interfaces.IUserPhoneRpc;
import org.live.user.provider.service.IUserPhoneService;
import org.live.user.provider.service.IUserService;
import org.live.user.provider.service.bo.UserRegisterBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:01
 */
@DubboService
public class UserPhoneRpcImpl implements IUserPhoneRpc {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPhoneRpcImpl.class);

    @Resource
    private IUserPhoneService userPhoneService;
    @Resource
    private IUserService userService;
    @DubboReference
    private IdBuilderRpc idBuilderRpc;
    @Resource
    private RedisTemplate<String, UserPhoneDTO> redisTemplate;
    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder cacheKeyBuilder;

    @Override
    public UserLoginDTO login(String phone) {
        if (phone == null) {
            return UserLoginDTO.loginError("手机号不能为空");
        }
        UserPhoneDTO userPhoneDTO = this.queryByPhone(phone);
        if (userPhoneDTO != null) {
            LOGGER.error("phone is {} 已经被注册,可以直接登录", phone);
            return UserLoginDTO.loginSuccess(userPhoneDTO.getUserId());
        }
        //进行注册操作
        UserRegisterBO userRegisterBO = registerUser(phone);
        LOGGER.error("userPhoneDTO is {} 注册成功，进行登录", userPhoneDTO);
        return UserLoginDTO.loginSuccess(userRegisterBO.getUserId());
    }

    private String createAndSaveToken(Long userId) {
        String token = UUID.randomUUID().toString();
        String tokenKey = cacheKeyBuilder.buildUserLoginTokenKey(token);
        stringObjectRedisTemplate.opsForValue().set(tokenKey, tokenKey, 30, TimeUnit.DAYS);
        return token;
    }

    private UserRegisterBO registerUser(String phone) {
        Long newUserId = idBuilderRpc.increaseSeqId(IdTypeEnum.USER_ID.getCode());
        //将手机号右移 2 位，然后取末尾两位数字
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(newUserId);
        userDTO.setNickName("用户-" + userDTO.getUserId());
        boolean effect = userService.insert(userDTO);
        if (!effect) {
            LOGGER.error("注册用户失败，userId = {}", newUserId);
        }
        effect = userPhoneService.insert(phone, newUserId);
        if (!effect) {
            LOGGER.error("注册手机号失败，userId = {}", newUserId);
        }
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setPhone(phone);
        userRegisterBO.setUserId(newUserId);
        return userRegisterBO;
    }

    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
        String redisKey = cacheKeyBuilder.buildUserPhoneListKey(userId);
        List<UserPhoneDTO> userPhoneCacheList = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (!CollectionUtils.isEmpty(userPhoneCacheList)) {
            //可能是缓存的空值
            if (userPhoneCacheList.get(0).getUserId() == null) {
                return Collections.emptyList();
            }
            return userPhoneCacheList;
        }
        userPhoneCacheList = userPhoneService.queryByUserId(userId);
        int expireTime = 30;
        if (CollectionUtils.isEmpty(userPhoneCacheList)) {
            //防止缓存击穿
            userPhoneCacheList = List.of(new UserPhoneDTO());
            expireTime = 5;
        }

        userPhoneCacheList.forEach(x -> x.setPhone(DESUtils.decrypt(x.getPhone())));
        redisTemplate.opsForList().leftPushAll(redisKey, userPhoneCacheList);
        redisTemplate.expire(redisKey, expireTime, TimeUnit.MINUTES);
        return userPhoneCacheList;
    }

    @Override
    public UserPhoneDTO queryByPhone(String phone) {
        String redisKey = cacheKeyBuilder.buildUserPhoneObjKey(phone);
        UserPhoneDTO userPhoneDTO = redisTemplate.opsForValue().get(redisKey);
        if (userPhoneDTO != null && userPhoneDTO.getUserId() != null) {
            return userPhoneDTO;
        }
        if (userPhoneDTO != null && userPhoneDTO.getUserId() == null) {
            return null;
        }
        userPhoneDTO = userPhoneService.queryByPhone(phone);
        if (userPhoneDTO != null) {
            userPhoneDTO.setPhone(DESUtils.decrypt(userPhoneDTO.getPhone()));
            redisTemplate.opsForValue().set(redisKey, userPhoneDTO, 30, TimeUnit.MINUTES);
            return userPhoneDTO;
        }
        //缓存一个空对象，防止缓存击穿
        redisTemplate.opsForValue().set(redisKey, new UserPhoneDTO(), 1, TimeUnit.MINUTES);
        return null;
    }
}
