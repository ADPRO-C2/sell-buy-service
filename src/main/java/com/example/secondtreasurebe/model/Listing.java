package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Listing {
    protected String userId;
    protected String listingId;
    protected String name;
    protected String description;
    protected int price;
    protected int stock;
    protected String photoUrl;
    protected int rateCondition;

    public abstract String getType();

    public Listing(String userId, String listingId, String name, String description, int price, int stock,
                   String photoUrl, int rateCondition) {
        this.userId = userId;
        this.listingId = listingId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;

    }
}