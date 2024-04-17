package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Listing {
    protected String listingId;
    protected String name;
    protected String description;
    protected int price;
    protected int stock;
    protected String photoUrl;
    protected int rateCondition; //0: baru; 1:masih bagus; 2:rusak ringan; 3:rusak sedang

    public abstract String getType();
}