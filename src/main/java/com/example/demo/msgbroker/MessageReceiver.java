package com.example.demo.msgbroker;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageReceiver implements MessageListener {
    
    @Autowired
    WorkerRepository workerRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        System.out.println(message.toString());
        // Handle the message received from the channel
        System.out.println("Received message on channel '" + channel + "': " + messageBody);
        System.out.println("Status saved: " + saveWorkerStatus(message));
    }
    
    String saveWorkerStatus(Message message) {
        String workerMessage = new String(message.getBody());
        WorkerStatus status = workerRepository.save(new WorkerStatus(0, workerMessage));
        return status.statusMessage;
    }
}
