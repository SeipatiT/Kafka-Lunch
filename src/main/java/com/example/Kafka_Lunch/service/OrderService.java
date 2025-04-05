package com.example.Kafka_Lunch.service;
import com.example.Kafka_Lunch.model.OrderRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final String ORDER_TOPIC = "foodspot";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processOrder(OrderRequest request) {
        String orderMessage = request.customerName() + " ordered " + request.item();
        kafkaTemplate.send(ORDER_TOPIC, orderMessage);
        System.out.println("Order placed: " + orderMessage);
    }
}