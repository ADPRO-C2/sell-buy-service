package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Checkout;

public interface CheckoutService {
    public Checkout createCheckout(Checkout checkout);
    public Checkout updateCheckout(Checkout checkout);
    public Checkout findCheckoutById(String userId);
    public void deleteCheckout(String userId);
}
