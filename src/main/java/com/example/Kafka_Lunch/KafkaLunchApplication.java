package com.example.Kafka_Lunch;

import com.example.Kafka_Lunch.model.Order;
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

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, Order> kafkaTemplate){
//        return args -> {
//            for(int i =0; i < 10; i++){
//                kafkaTemplate.send("foodspot","hello kafka :)"+ i); }
//        };
//    }

}
