package com.example.Kafka_Lunch.model;

public record MessageRequest(String customerName, String item, Integer quantity, String status) {
}
