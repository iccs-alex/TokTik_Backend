package com.example.demo.msgbroker;

import java.util.List;
import com.example.demo.msgbroker.Message;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.s3.VideoDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/api/publish")
    public void publish(@RequestBody Message message) {
        System.out.println("Sending to redis: " + message.getVideoKey());
        addToRedis(message.getId().toString(), message.getVideoKey());
    }

    // Use RedisTemplate to interact with Redis
    public void addToRedis(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
