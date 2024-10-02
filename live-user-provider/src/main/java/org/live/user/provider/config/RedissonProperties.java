package org.live.user.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LIMBO0523
 * @Date 2024/10/2 10:21
 */
@ConfigurationProperties(prefix = "redisson")
@Configuration
public class RedissonProperties {
    private String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "RedissonProperties{" +
                "Address='" + Address + '\'' +
                '}';
    }
}
