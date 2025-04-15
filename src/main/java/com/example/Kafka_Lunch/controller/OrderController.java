package com.example.Kafka_Lunch.controller;
import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.model.OrderRequest;
import com.example.Kafka_Lunch.service.OrderService;
import com.example.Kafka_Lunch.service.OrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping(value = "api/v1/order")
@Tag(name = "Order Controller", description = "APIs for placing and managing orders")
public class OrderController {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;

    public OrderController(KafkaTemplate<String, Order> kafkaTemplate,
                           OrderService orderService,
                           OrderStatusService orderStatusService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderService = orderService;
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // Return the template name without .html extension
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

    // Keep existing methods

    // Add the template method from MessageController
    @GetMapping("/status-page")
    public String statusPage() {
        return "next"; // Return the template name without .html extension
    }

    // Consolidated order status endpoint
    @GetMapping("/status")
    @ResponseBody
    @Operation(summary = "Get order status", description = "Get the status of an order by customer name and item")
    public ResponseEntity<Order> getOrderStatus(
            @RequestParam String customerName,
            @RequestParam String item) {
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


    @PostMapping("/bulk")
    @ResponseBody
    @Operation(summary = "Create multiple orders at once", description = "Generate a specified number of orders with random customer names and items")
    public ResponseEntity<List<Order>> createBulkOrders(@RequestParam(defaultValue = "100") int count) {
        List<Order> createdOrders = new ArrayList<>();

        // Array of sample names (first names only)
        String[] customerNames = {
                "John", "Jane", "Alex", "Emma", "Michael", "Olivia", "William", "Sophia",
                "James", "Isabella", "Robert", "Mia", "David", "Charlotte", "Joseph", "Amelia",
                "Daniel", "Harper", "Matthew", "Evelyn", "Sarah", "Thomas", "Emily", "Kevin",
                "Anna", "George", "Lisa", "Steven", "Mary", "Chris", "Laura", "Andrew"
        };

        String[] items = {"BURGER", "PIZZA", "SALAD","CHICKEN"};
        Random random = new Random();

        // Generate the specified number of orders
        for (int i = 0; i < count; i++) {
            String customerName = customerNames[random.nextInt(customerNames.length)];
            // Add index to ensure uniqueness when same name is selected multiple times
            customerName = customerName + "_" + i;

            String item = items[random.nextInt(items.length)];
            int quantity = random.nextInt(5) + 1; // Random quantity between 1 and 5

            OrderRequest request = new OrderRequest(customerName, item, quantity, null);
            Order order = orderService.processOrder(request);
            createdOrders.add(order);
        }

        return ResponseEntity.ok(createdOrders);
    }
}