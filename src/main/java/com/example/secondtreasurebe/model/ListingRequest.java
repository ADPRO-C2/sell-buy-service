package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingRequest {
    private String listingId;
    private final String userId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String photoUrl;
    private int rateCondition;

    public ListingRequest(String userId){
        this.userId=userId;
    }

    public void validate() {
        if (price < 0 || stock < 0 || rateCondition>3 || rateCondition<0) {
            throw new IllegalArgumentException("Price and stock must be non-negative");
        }
    }
}
