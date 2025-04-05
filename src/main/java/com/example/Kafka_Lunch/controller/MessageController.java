package com.example.Kafka_Lunch.controller;

import com.example.Kafka_Lunch.model.MessageRequest;
import com.example.Kafka_Lunch.model.Order;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/v1/messages")
@Tag(name = "Message Controller", description = "APIs for placing and managing orders")
public class MessageController {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public MessageController(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/next")
    public String index() {
        return "next.html"; // Refers to next.html in templates folder
    }

    // Simple status endpoint
    @PostMapping("/status")
    @ResponseBody
    public String getStatus(@RequestBody MessageRequest request) {
        return "Status for " + request.customerName() + ": Processing";
    }
}