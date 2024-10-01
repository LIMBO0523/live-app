package org.idea.live.framework.redis.starter.key;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 17:01
 */
public class RedisKeyBuilder {

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String SPLIT_ITEM = ":";

    public String getSplitItem() {
        return SPLIT_ITEM;
    }

    public String getPrefix() {
        return applicationName + SPLIT_ITEM;
    }
}

