package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OrderProcessor {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final Random random = new Random();
    private final SimpMessagingTemplate messagingTemplate;

    public OrderProcessor(KafkaTemplate<String, Order> kafkaTemplate, SimpMessagingTemplate messagingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void processOrder(Order order) {
        // Simulate processing time
        try {
            Thread.sleep(5000); // 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean isAccepted = random.nextBoolean(); // Randomly accept/reject
        order.setStatus(isAccepted ? "Accepted" : "Rejected");

        // Send to Kafka
        kafkaTemplate.send("order-confirmations", order);

        // Send to WebSocket for real-time updates
        messagingTemplate.convertAndSend("/topic/orders/" + order.getCustomerName(), order);
    }
}