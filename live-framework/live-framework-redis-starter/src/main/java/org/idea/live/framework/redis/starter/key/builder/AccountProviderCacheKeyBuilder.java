package org.idea.live.framework.redis.starter.key.builder;

import org.idea.live.framework.redis.starter.key.RedisKeyBuilder;
import org.idea.live.framework.redis.starter.key.RedisKeyLoadMatch;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 12:03
 */
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class AccountProviderCacheKeyBuilder extends RedisKeyBuilder {

    private static String ACCOUNT_TOKEN_KEY = "account";

    public String buildUserLoginTokenKey(String key) {
        return super.getPrefix() + ACCOUNT_TOKEN_KEY + super.getSplitItem() + key;
    }
}
