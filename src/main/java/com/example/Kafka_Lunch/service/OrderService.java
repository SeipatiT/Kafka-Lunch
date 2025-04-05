package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.model.OrderRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private static final String ORDER_TOPIC = "orders"; // Changed from "foodspot" to match your config
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order processOrder(OrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(
                orderId,
                request.customerName(),
                request.item(),
                "Pending",
                request.quantity()
        );

        kafkaTemplate.send(ORDER_TOPIC, order);
        System.out.println("Order placed: " + order.getCustomerName() + " ordered " + order.getItem());
        return order;
    }
}