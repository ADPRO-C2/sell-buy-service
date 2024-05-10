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

    public void addCartListing(CartListing cartListing) {
        this.items.add(cartListing);
    }

    public void removeCartListing(CartListing cartListing) {
            this.items.remove(cartListing);
        }

    public void editCartListing(CartListing cartListing, int newAmount) {
        for (CartListing cl : this.items) {
            if (cl.getCartListingId().equals(cartListing.getCartListingId())) {
                cl.setAmount(newAmount);
                break;
            }
        }
    }
}
