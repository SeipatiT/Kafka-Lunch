package com.example.Kafka_Lunch.service;

import com.example.Kafka_Lunch.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class OrderManagementService {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private static final String ORDER_CONFIRMATIONS_TOPIC = "order-confirmations";
    private static final String ORDER_STATUS_UPDATES_TOPIC = "order-status-updates";

    public OrderManagementService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Store an order in the in-memory repository
    public void storeOrder(Order order) {
        orders.put(order.getId(), order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    // Get orders by item type
    public List<Order> getOrdersByItem(String item) {
        return orders.values().stream()
                .filter(order -> item.equalsIgnoreCase(order.getItem()))
                .collect(Collectors.toList());
    }

    // Update order status and publish to order-confirmations topic
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }

        order.setStatus(status);
        orders.put(orderId, order);

        // Publish the updated order to both topics
        kafkaTemplate.send(ORDER_CONFIRMATIONS_TOPIC, order);
        kafkaTemplate.send(ORDER_STATUS_UPDATES_TOPIC, order);

        return order;
    }

    // Search for orders by item name (case-insensitive) and update their status
    public List<Order> searchAndUpdateOrdersByItem(String item, String newStatus) {
        List<Order> matchingOrders = orders.values().stream()
                .filter(order -> item.equalsIgnoreCase(order.getItem()))
                .collect(Collectors.toList());

        // Update status of matching orders
        matchingOrders.forEach(order -> {
            order.setStatus(newStatus);
            orders.put(order.getId(), order);

            // Publish to both topics
            kafkaTemplate.send(ORDER_CONFIRMATIONS_TOPIC, order);
            kafkaTemplate.send(ORDER_STATUS_UPDATES_TOPIC, order);
        });

        return matchingOrders;
    }
}