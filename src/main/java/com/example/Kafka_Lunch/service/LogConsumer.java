package com.example.Kafka_Lunch.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    @KafkaListener(topics = "application-logs", groupId = "log-group")
    public void processLog(String log) {
        // Process log message as needed
        System.out.println("Log received: " + log);
    }
}