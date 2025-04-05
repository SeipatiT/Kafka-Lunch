package com.example.Kafka_Lunch.service;
import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OrderProcessor {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final Random random = new Random();

    public OrderProcessor(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void processOrder(Order order) {
        boolean isAccepted = random.nextBoolean(); // Randomly accept/reject
        order.setStatus(isAccepted ? "Accepted" : "Rejected");
        kafkaTemplate.send("order-confirmations", order);
    }
}