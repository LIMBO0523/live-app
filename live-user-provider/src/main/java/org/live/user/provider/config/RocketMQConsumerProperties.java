package org.live.user.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 18:47
 */
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Configuration
public class RocketMQConsumerProperties {

    // rocketmq的nameServer地址
    private String nameServer;
    // 生产者组名称
    private String groupName;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "RocketMQConsumerProperties{" +
                "nameServer='" + nameServer + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
