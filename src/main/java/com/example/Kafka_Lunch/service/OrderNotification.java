package com.example.Kafka_Lunch.service;
import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderNotification {
    @KafkaListener(topics = "order-confirmations", groupId = "order-group")
    public void notifyUser(Order order) {
        if ("Accepted".equals(order.getStatus())) {
            System.out.println("Order " + order.getId() + " for " + order.getItem() + " is ready!");
        } else {
            System.out.println("Order " + order.getId() + " was rejected.");
        }
    }
}