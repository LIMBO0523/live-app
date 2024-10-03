package org.live.account.provider.services.impl;

import jakarta.annotation.Resource;
import org.idea.live.framework.redis.starter.key.builder.AccountProviderCacheKeyBuilder;
import org.live.account.provider.services.IAccountTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 12:00
 */
@Service
public class AccountTokenServiceImpl implements IAccountTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTokenServiceImpl.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private AccountProviderCacheKeyBuilder accountProviderCacheKeyBuilder;

    @Override
    public String createAndSaveLoginToken(Long userId) {
        String tokenKey = UUID.randomUUID().toString();
        String loginTokenKey = accountProviderCacheKeyBuilder.buildUserLoginTokenKey(tokenKey);
        redisTemplate.opsForValue().set(loginTokenKey, String.valueOf(userId), 30, TimeUnit.DAYS);
        return tokenKey;
    }

    @Override
    public Long getUserIdByToken(String tokenKey) {
        String loginTokenKey = accountProviderCacheKeyBuilder.buildUserLoginTokenKey(tokenKey);
        Object value = redisTemplate.opsForValue().get(loginTokenKey);
        if (value == null) {
            return null;
        }
        return Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(loginTokenKey)));
    }
}
