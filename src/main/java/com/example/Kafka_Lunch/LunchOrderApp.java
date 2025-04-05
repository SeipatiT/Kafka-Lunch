package com.example.Kafka_Lunch;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Properties;
import java.util.Arrays;

public class LunchOrderApp {

    private static final String ORDER_TOPIC = "lunch_orders";
    private static final String CONFIRMATION_TOPIC = "order_confirmations";
    private static final String READY_TOPIC = "order_ready";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        Thread producerThread = new Thread(() -> orderProducer("Burger"));
        Thread consumerThread = new Thread(LunchOrderApp::orderConsumer);
        Thread notificationConsumerThread = new Thread(LunchOrderApp::notificationConsumer);

        producerThread.start();
        consumerThread.start();
        notificationConsumerThread.start();
    }

    public static void orderProducer(String order) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>(ORDER_TOPIC, "User1", order));
            System.out.println("Order placed: " + order);
        }
    }

    public static void orderConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", "order-processing");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(ORDER_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record -> {
                    String orderStatus = Math.random() > 0.2 ? "Accepted" : "Not Accepted";
                    System.out.println("Processing order: " + record.value() + " - Status: " + orderStatus);
                    sendConfirmation(record.key(), orderStatus);
                    if (orderStatus.equals("Accepted")) {
                        try {
                            Thread.sleep(5000); // Simulate order preparation
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendReadyNotification(record.key());
                    }
                });
            }
        }
    }

    public static void sendConfirmation(String user, String status) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>(CONFIRMATION_TOPIC, user, status));
            System.out.println("Order confirmation sent: " + status);
        }
    }

    public static void sendReadyNotification(String user) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>(READY_TOPIC, user, "Your order is ready!"));
            System.out.println("Order ready notification sent.");
        }
    }

    public static void notificationConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", "notifications");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(READY_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record -> {
                    System.out.println("User notification: " + record.value());
                });
            }
        }
    }
}
