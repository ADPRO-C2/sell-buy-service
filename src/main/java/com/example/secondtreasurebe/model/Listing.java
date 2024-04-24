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
}