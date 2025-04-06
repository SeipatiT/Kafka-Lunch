package com.example.Kafka_Lunch.controller;

import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.service.OrderManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/owner")
@Tag(name = "Restaurant Owner Controller", description = "APIs for restaurant owners to manage orders")
public class RestaurantOwnerController {

    private final OrderManagementService orderManagementService;

    public RestaurantOwnerController(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "owner-dashboard.html";
    }

    @GetMapping("/orders")
    @ResponseBody
    @Operation(summary = "Get all orders", description = "Retrieve all orders from the system")
    public List<Order> getAllOrders() {
        return orderManagementService.getAllOrders();
    }

    @GetMapping("/orders/filter")
    @ResponseBody
    @Operation(summary = "Filter orders by item", description = "Retrieve orders for a specific food item")
    public List<Order> getOrdersByItem(@RequestParam String item) {
        return orderManagementService.getOrdersByItem(item);
    }

    @PostMapping("/orders/{orderId}/status")
    @ResponseBody
    @Operation(summary = "Update order status", description = "Update the status of a specific order")
    public Order updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return orderManagementService.updateOrderStatus(orderId, status);
    }

    @PostMapping("/orders/search-and-update")
    @ResponseBody
    @Operation(summary = "Search and update orders by item", description = "Search for orders by item name and update their status")
    public List<Order> searchAndUpdateOrdersByItem(@RequestParam String item, @RequestParam String newStatus) {
        return orderManagementService.searchAndUpdateOrdersByItem(item, newStatus);
    }
}