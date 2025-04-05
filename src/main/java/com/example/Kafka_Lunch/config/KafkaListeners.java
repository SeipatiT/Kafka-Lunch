package com.example.Kafka_Lunch.config;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "orders", groupId = "order-group")
    void listener(Order data){
        System.out.println("Listener received order: " + data.getCustomerName() + " - " + data.getItem());
    }
}