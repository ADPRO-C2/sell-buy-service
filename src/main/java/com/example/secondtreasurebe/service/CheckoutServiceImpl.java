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
    public Checkout updateCheckout(Checkout checkout) {
        Checkout check = checkoutRepository.update(checkout);
        if (check != null) {
            return check;
        } else {
            throw new NoSuchElementException("Checkout not found.");
        }
    }

    @Override
    public Checkout findCheckoutById(String userId) {
        Checkout check = checkoutRepository.findById(userId);
        if (check != null) {
            return check;
        } else {
            throw new NoSuchElementException("Checkout not found.");
        }
    }

    @Override
    public void deleteCheckout(String userId) {
        if (checkoutRepository.findById(userId) != null) {
            checkoutRepository.delete(userId);
        } else {
            throw new NoSuchElementException("Checkout not found.");
        }
    }
}
