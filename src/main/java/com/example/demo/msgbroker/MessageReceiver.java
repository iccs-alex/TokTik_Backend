package com.example.demo.msgbroker;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class MessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Receiving messages, consumed event : " + message);
    }
}
