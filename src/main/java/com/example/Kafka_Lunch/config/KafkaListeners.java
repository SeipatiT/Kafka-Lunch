package com.example.Kafka_Lunch.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "foodspot", groupId = "groupID")
    void listener(String data){
        System.out.println("Listener received order: " + data);
    }
}
