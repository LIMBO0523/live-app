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
import org.idea.live.framework.redis.starter.key.builder.UserProviderCacheKeyBuilder;
import org.live.user.constants.CacheAsyncDeleteCode;
import org.live.user.dto.UserCacheAsyncDeleteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.live.user.constants.UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC;

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
    private RedisTemplate<String, Object> redisTemplate; // <String, Object>
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;


    public void initConsumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
            consumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName());
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe(CACHE_ASYNC_DELETE_TOPIC, "*");
            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = JSON.parseObject(new String(msgs.get(0).getBody()), UserCacheAsyncDeleteDTO.class);
                    if (CacheAsyncDeleteCode.USER_INFO_DELETE.getCode() == userCacheAsyncDeleteDTO.getCode()) {
                        Long userId = JSON.parseObject(userCacheAsyncDeleteDTO.getJson()).getLong("userId");
                        redisTemplate.delete(userProviderCacheKeyBuilder.buildUserInfoKey(userId));
                    }else if (CacheAsyncDeleteCode.USER_TAG_DELETE.getCode() == userCacheAsyncDeleteDTO.getCode()) {
                        Long userId = JSON.parseObject(userCacheAsyncDeleteDTO.getJson()).getLong("userId");
                        redisTemplate.delete(userProviderCacheKeyBuilder.buildTagKey(userId));
                    }
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
