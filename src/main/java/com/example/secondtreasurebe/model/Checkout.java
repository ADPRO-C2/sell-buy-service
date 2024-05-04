package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Checkout {
    private List<Order> orders;
    private String userId;

    public Checkout(String userId) {
        this.userId = userId;
        this.orders = new ArrayList<>();
    }

    public void sameUser() {
        for (Order order : this.orders) {
            if (!order.getUserId().equals(this.userId)) {
                throw new IllegalArgumentException("User ID of Orders and Checkout must match.");
            }
        }
    }
}
