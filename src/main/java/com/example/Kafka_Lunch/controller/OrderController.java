package com.example.Kafka_Lunch.controller;
import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.model.OrderRequest;
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
    Order order;

    public OrderController(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/index")
    public String index() {
        return "next.html"; // Refers to index.html in templates folder
    }


    @PostMapping
    @ResponseBody  // <--- This annotation makes the returned string be the response body.
    @Operation(summary = "Place an order", description = "Submit an order for Burger, Pizza, or Salad")
    public String placeOrder(@RequestBody OrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        order = new Order(orderId, request.customerName(), request.item(),  "Pending", request.quantity());

        kafkaTemplate.send("orders", order); // Now Kafka will correctly serialize it

        return "Order placed: " + order.getItem();
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Check an order", description = "Order status")
    public String getOrderStatus(@RequestBody Order request) {

        Order order = new Order(orderId, request.item(), "Pending");

        kafkaTemplate.send("orders", order); // Now Kafka will correctly serialize it

        return "Order placed: " + order.getItem();
    }
}




