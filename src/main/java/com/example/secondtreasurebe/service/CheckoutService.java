package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.model.Order;

import java.util.List;

public interface CheckoutService {
    public Checkout createCheckout(Checkout checkout);
    public Checkout findCheckoutById(String userId);
    public void deleteCheckout(String userId);
    public List<Order> findAllInCheckout(String userId);
}
