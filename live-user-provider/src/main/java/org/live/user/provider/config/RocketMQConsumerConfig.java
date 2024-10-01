package org.live.user.provider.config;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.idea.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.live.user.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 消费者配置
 * @Author LIMBO0523
 * @Date 2024/10/1 18:49
 */
@Configuration
public class RocketMQConsumerConfig implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQConsumerConfig.class);

    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;
    @Resource
    private RedisTemplate<String, UserDTO> redisTemplate; // <String, Object>
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;

    public void initConsumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
            consumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName());
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe("user-update-cache", "*");
            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    UserDTO userDTO = JSON.parseObject(new String(msgs.get(0).getBody()), UserDTO.class);
                    if (userDTO == null || userDTO.getUserId() == null) {
                        LOGGER.error("用户id为空, msg:{}", new String(msgs.get(0).getBody()));
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    redisTemplate.delete(userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId()));
                    LOGGER.info("延迟删除处理，userDTO is {}", userDTO);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            LOGGER.info("MQ消费者启动成功, nameServer is {}", rocketMQConsumerProperties.getNameServer());
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void afterPropertiesSet() throws Exception {
        initConsumer();
    }
}
