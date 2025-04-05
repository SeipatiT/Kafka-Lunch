package com.example.Kafka_Lunch.controller;
import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.model.OrderRequest;
import com.example.Kafka_Lunch.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(value = "api/v1/order")
@Tag(name = "Order Controller", description = "APIs for placing and managing orders")
public class OrderController {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderService orderService;

    public OrderController(KafkaTemplate<String, Order> kafkaTemplate, OrderService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderService = orderService;
    }

    @GetMapping("/index")
    public String index() {
        return "index.html"; // Return the template name without .html extension
    }

    @PostMapping
    @ResponseBody  // <--- This annotation makes the returned string be the response body.
    @Operation(summary = "Place an order", description = "Submit an order for Burger, Pizza, or Salad")
    public String placeOrder(@RequestBody OrderRequest request) {
        // Use OrderService to process the order
        Order order = orderService.processOrder(request);
        return "Order placed: " + order.getItem();
    }

    // Make sure there's no conflict with the duplicate @PostMapping annotation
    @PostMapping("/status")
    @ResponseBody
    @Operation(summary = "Check an order", description = "Order status")
    public String getOrderStatus(@RequestBody OrderRequest request) {
        // In a real system, you'd look up the order status in a database
        return "Order for " + request.customerName() + " is being processed";
    }
}