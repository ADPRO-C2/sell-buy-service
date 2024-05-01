package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private String orderId;
    private String userId;
    private List<CartListing> items;
    private double priceTotal;
    private String status;

    public Order(String userId, Cart cart) {
        this.userId = userId;
        this.items = cart.getItems();
        this.priceTotal = calculatePriceTotal();
        this.status = "Dikemas";
    }

    private double calculatePriceTotal() {
        double total = 0;
        for (CartListing item : items) {
            total += item.getAmount() * item.getListing().getPrice();
        }
        return total;
    }
}
