package com.example.Kafka_Lunch.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("orders").build();
    }

    @Bean
    public NewTopic confirmationTopic() {
        return TopicBuilder.name("order-confirmations").build();
    }

    @Bean
    public NewTopic statusUpdateTopic() {
        return TopicBuilder.name("order-status-updates").build();
    }

}