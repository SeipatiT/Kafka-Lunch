package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderStatusService {

    private final Map<String, Order> customerOrderStatus = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public OrderStatusService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "order-status-updates", groupId = "order-group")
    public void processStatusUpdate(Order order) {
        // Store the order with customer name as key for quick lookup
        String key = getCustomerOrderKey(order.getCustomerName(), order.getItem());
        customerOrderStatus.put(key, order);

        // Send update to WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/status/" + key, order);

        System.out.println("Status update for customer " + order.getCustomerName() +
                " - Item: " + order.getItem() + " - Status: " + order.getStatus());
    }

    // Also listen to the main orders topic to capture initial orders
    @KafkaListener(topics = "orders", groupId = "status-group")
    public void captureNewOrder(Order order) {
        String key = getCustomerOrderKey(order.getCustomerName(), order.getItem());
        customerOrderStatus.put(key, order);
    }

    // Store order confirmations too
    @KafkaListener(topics = "order-confirmations", groupId = "status-group")
    public void captureOrderConfirmation(Order order) {
        String key = getCustomerOrderKey(order.getCustomerName(), order.getItem());
        customerOrderStatus.put(key, order);

        // Send update to WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/status/" + key, order);
    }

    public Order getOrderStatus(String customerName, String item) {
        String key = getCustomerOrderKey(customerName, item);
        return customerOrderStatus.get(key);
    }

    private String getCustomerOrderKey(String customerName, String item) {
        return customerName + "_" + item;
    }
}