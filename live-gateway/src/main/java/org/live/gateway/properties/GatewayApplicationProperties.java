package org.live.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 13:00
 */
@ConfigurationProperties(prefix = "gateway")
@Configuration
@RefreshScope
public class GatewayApplicationProperties {

    private List<String> notCheckUrlList;

    public List<String> getNotCheckUrlList() {
        return notCheckUrlList;
    }

    public void setNotCheckUrlList(List<String> notCheckUrlList) {
        this.notCheckUrlList = notCheckUrlList;
    }

    @Override
    public String toString() {
        return "GatewayApplicationProperties{" +
                "notCheckUrlList=" + notCheckUrlList +
                '}';
    }
}
