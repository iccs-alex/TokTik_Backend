package com.example.demo.msgbroker;

import java.util.List;
import com.example.demo.msgbroker.Message;
import com.example.demo.videos.VideoDetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping("/api/publish")
    public void publish(@RequestBody Message message) {
        sendDataToChannel("convert", message.getVideoKey());
    }

    public void sendDataToChannel(String channel, String data) {
        redisTemplate.convertAndSend(channel, data);
    }

    @GetMapping("/api/worker_status")
    public String getWorkerStatus() {
        return workerRepository.findById(0);
    }

}
