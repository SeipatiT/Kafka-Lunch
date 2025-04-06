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

        // Publish the updated order to the order-confirmations topic
        kafkaTemplate.send("order-confirmations", order);

        return order;
    }
}