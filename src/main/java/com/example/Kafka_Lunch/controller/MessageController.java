package com.example.Kafka_Lunch.controller;

import com.example.Kafka_Lunch.model.MessageRequest;
import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.service.OrderService;
import com.example.Kafka_Lunch.service.OrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/messages")
@Tag(name = "Message Controller", description = "APIs for placing and managing orders")
public class MessageController {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;

    public MessageController(KafkaTemplate<String, Order> kafkaTemplate,
                             OrderService orderService,
                             OrderStatusService orderStatusService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderService = orderService;
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/next")
    public String index() {
        return "next"; // Return the template name without .html extension
    }

    // Simple status endpoint
    @PostMapping("/status")
    @ResponseBody
    public String getStatus(@RequestBody MessageRequest request) {
        return "Status for " + request.customerName() + ": Processing";
    }

    // New endpoint to get order status
    @GetMapping("/orderstatus")
    @ResponseBody
    @Operation(summary = "Get order status", description = "Get the status of an order by customer name and item")
    public ResponseEntity<Order> getOrderStatus(@RequestParam String customerName, @RequestParam String item) {
        Order order = orderStatusService.getOrderStatus(customerName, item);

        if (order == null) {
            // If order not found, create a placeholder order with "Pending" status
            order = new Order(
                    "not-found",
                    customerName,
                    item,
                    "Pending",
                    1
            );
        }

        return ResponseEntity.ok(order);
    }
}