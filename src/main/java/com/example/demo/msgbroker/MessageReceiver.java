package com.example.demo.msgbroker;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class MessageReceiver implements MessageListener {

@Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        System.out.println(message.toString());
        // Handle the message received from the channel
        System.out.println("Received message on channel '" + channel + "': " + messageBody);
    }
    

}
