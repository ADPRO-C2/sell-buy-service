package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Order> findAllInCheckout(String userId) {
        Checkout checkout = findCheckoutById(userId);
        if (checkout != null) {
            return checkout.getOrders();
        } else {
            throw new NoSuchElementException("Checkout not found for user with ID: " + userId);
        }
    }
}
