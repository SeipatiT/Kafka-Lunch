package com.example.Kafka_Lunch.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private static final String LOG_TOPIC = "application-logs";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public LoggingService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void log(String key, String message) {
        kafkaTemplate.send(LOG_TOPIC, key, message);
        System.out.println("Log sent to Kafka - Key: " + key + ", Message: " + message);
    }

    // Overloaded method for when you don't need a specific key
    public void log(String message) {
        log("INFO", message);
    }

    // Specific log levels
    public void logInfo(String message) {
        log("INFO", message);
    }

    public void logWarning(String message) {
        log("WARNING", message);
    }

    public void logError(String message) {
        log("ERROR", message);
    }

    public void logDebug(String message) {
        log("DEBUG", message);
    }
}