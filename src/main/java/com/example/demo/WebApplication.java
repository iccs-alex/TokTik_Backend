package com.example.demo;

import com.example.demo.msgbroker.Message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(WebApplication.class, args);
	}

    
    @Bean
    public ReactiveRedisOperations<String, Message> msgTemplate(LettuceConnectionFactory connectionFactory) {
        RedisSerializer<Message> msgSerializer = new Jackson2JsonRedisSerializer<>(Message.class);
        RedisSerializationContext<String, Message> context = RedisSerializationContext.<String, Message>newSerializationContext(RedisSerializer.string())
                .value(msgSerializer)
                .build();
        
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
