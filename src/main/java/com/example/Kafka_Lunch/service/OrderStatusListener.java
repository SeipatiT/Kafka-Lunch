package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusListener {

    @KafkaListener(topics = "order-status-updates", groupId = "order-group")
    public void processStatusUpdate(Order order) {
        System.out.println("Status update received: Order ID " + order.getId() +
                " - Item: " + order.getItem() +
                " - Status changed to: " + order.getStatus());
    }
}