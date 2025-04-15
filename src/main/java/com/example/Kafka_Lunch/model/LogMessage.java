package com.example.Kafka_Lunch.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class LogMessage {
    private String level;
    private String message;
    private String source;
    private LocalDateTime timestamp;

    public LogMessage() {}

    @JsonCreator
    public LogMessage(
            @JsonProperty("level") String level,
            @JsonProperty("message") String message,
            @JsonProperty("source") String source) {
        this.level = level;
        this.message = message;
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getLevel() { return level; }
    public String getMessage() { return message; }
    public String getSource() { return source; }
    public LocalDateTime getTimestamp() { return timestamp; }
}