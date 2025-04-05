package com.example.Kafka_Lunch.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String id;

    private String customerName;
    private String item;
    private String status; // e.g., "Pending", "Accepted", "Rejected"

    private Integer quantity;

    public Message(){}

    @JsonCreator
    public Message(@JsonProperty("id") String id,
                 @JsonProperty("customerName") String customerName,
                 @JsonProperty("item") String item,
                 @JsonProperty("status") String status,
                 @JsonProperty("quantity") Integer quantity) {
        this.id = id;
        this.customerName = customerName;
        this.item = item;
        this.status = status;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getItem() { return item; }
    public String getCustomerName() {
        return customerName;
    }

    public Integer getQuantity() {
        return quantity;
    }


    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}