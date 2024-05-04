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
}
