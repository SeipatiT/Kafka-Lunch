package com.example.Kafka_Lunch;

import com.example.Kafka_Lunch.model.Order;
import com.example.Kafka_Lunch.service.LoggingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaLunchApplication {

    public static void main(String[] args) {

        SpringApplication.run(KafkaLunchApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Order> kafkaTemplate){
        return args -> {
            System.out.println("Application started successfully!");
            // You can add test messages here if needed
        };
    }

    @Bean
    CommandLineRunner testLogging(LoggingService loggingService) {
        return args -> {
            System.out.println("Testing logging service...");
            loggingService.logInfo("Application startup - test log message");
            loggingService.logWarning("This is a warning test message");
            loggingService.logError("This is an error test message");
        };
    }

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, Order> kafkaTemplate){
//        return args -> {
//            for(int i =0; i < 10; i++){
//                kafkaTemplate.send("foodspot","hello kafka :)"+ i); }
//        };
//    }

}
