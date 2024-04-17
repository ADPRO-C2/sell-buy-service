package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Cart {
    private String userId;
    private List<Listing> inCart;
    private List<Listing> checkout;
}