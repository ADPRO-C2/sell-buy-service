package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListing extends Listing {
    private int amount;

    public CartListing(String listingId, int amount) {
        this.listingId = listingId;
        this.amount = amount;
    }

    @Override
    public String getType() {
        return "cart";
    }
}
