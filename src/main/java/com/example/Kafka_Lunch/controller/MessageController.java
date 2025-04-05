package com.example.Kafka_Lunch.controller;

import com.example.Kafka_Lunch.model.MessageRequest;
import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/messages")
@Tag(name = "Message Controller", description = "APIs for placing and managing orders")
public class MessageController {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderService orderService;

    public MessageController(KafkaTemplate<String, Order> kafkaTemplate, OrderService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderService = orderService;
    }

    @GetMapping("/next")
    public String index() {
        return "next.html"; // Return the template name without .html extension
    }

    // Simple status endpoint
    @PostMapping("/status")
    @ResponseBody
    public String getStatus(@RequestBody MessageRequest request) {
        return "Status for " + request.customerName() + ": Processing";
    }
}