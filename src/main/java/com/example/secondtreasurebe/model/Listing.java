package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Listing {
    private String userId;
    private String listingId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String photoUrl;
    private int rateCondition; //0: baru; 1:masih bagus; 2:rusak ringan; 3:rusak sedang

    public void validate() {
        if (price < 0 || stock < 0 || rateCondition>3 || rateCondition<0) {
            throw new IllegalArgumentException("Price and stock must be non-negative");
        }
    }
}