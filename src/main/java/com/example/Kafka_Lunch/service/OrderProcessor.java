package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessor {
    private final SimpMessagingTemplate messagingTemplate;
    private final OrderManagementService orderManagementService;

    public OrderProcessor(SimpMessagingTemplate messagingTemplate, OrderManagementService orderManagementService) {
        this.messagingTemplate = messagingTemplate;
        this.orderManagementService = orderManagementService;
    }

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void receiveOrder(Order order) {
        // Set initial status if not set
        if (order.getStatus() == null) {
            order.setStatus("Pending");
        }

        // Store the order in the management service
        orderManagementService.storeOrder(order);

        // Send to WebSocket for real-time updates
        messagingTemplate.convertAndSend("/topic/orders", order);
    }
}