package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cart {
    private int cartId;
    private int userId;
    private int totalPrice;
    private Listing[] inCart;
}