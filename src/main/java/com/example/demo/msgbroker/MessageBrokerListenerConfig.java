package com.example.demo.msgbroker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class MessageBrokerListenerConfig {

@Bean
    public MessageListener myMessageListener() {
        return new MessageReceiver();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        System.out.println("Creating channels");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(myMessageListener(), new ChannelTopic("convert"));
        container.addMessageListener(myMessageListener(), new ChannelTopic("thumbnail"));
        container.addMessageListener(myMessageListener(), new ChannelTopic("process"));
        container.addMessageListener(myMessageListener(), new ChannelTopic("backend"));
        // Add more channels as needed
        return container;
    }
}

