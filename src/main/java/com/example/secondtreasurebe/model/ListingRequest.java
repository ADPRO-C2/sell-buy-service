package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingRequest {
    private String listingId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String photoUrl;
    private int rateCondition;
}