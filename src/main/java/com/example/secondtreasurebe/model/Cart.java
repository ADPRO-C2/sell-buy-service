package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class Cart {
    private String userId;
    private List<CartListing> items;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }
}
