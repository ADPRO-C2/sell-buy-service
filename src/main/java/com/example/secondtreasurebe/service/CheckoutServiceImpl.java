package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    public Checkout createCheckout(Checkout checkout) {
        return checkoutRepository.save(checkout);
    }

    @Override
    public Checkout findCheckoutById(String userId) {
        return checkoutRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(("Checkout not found.")));
    }

    @Override
    public void deleteCheckout(String userId) {
        if (!checkoutRepository.existsById(userId)) {
            throw new NoSuchElementException("Checkout not found.");
        } else {
            checkoutRepository.deleteById(userId);
        }
    }
}
