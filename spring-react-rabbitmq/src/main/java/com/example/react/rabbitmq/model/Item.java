package com.example.react.rabbitmq.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String id;
    private String name;
    private String description;
    private double price;

    protected Item() {
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}