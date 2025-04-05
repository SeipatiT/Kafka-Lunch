package com.example.Kafka_Lunch.model;

public record OrderRequest(String customerName, String item, Integer quantity, String status) {
}
