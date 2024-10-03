package org.idea.live.framework.redis.starter.key.builder;

import org.idea.live.framework.redis.starter.key.RedisKeyBuilder;
import org.idea.live.framework.redis.starter.key.RedisKeyLoadMatch;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 9:32
 */
@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class MsgProviderCacheKeyBuilder extends RedisKeyBuilder {

    private static String SMS_LOGIN_CODE_KEY = "smsLoginCode";

    public String buildSmsLoginCodeKey(String phone) {
        return super.getPrefix() + SMS_LOGIN_CODE_KEY + super.getSplitItem() + phone;
    }
}