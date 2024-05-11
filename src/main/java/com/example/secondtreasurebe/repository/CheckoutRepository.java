package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Checkout;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class CheckoutRepository {
    private List<Checkout> checkoutData = new ArrayList<>();

    public Checkout save(Checkout checkout) {
        checkoutData.add(checkout);
        return checkout;
    }

    public Checkout update(Checkout checkout) {
        String userId = checkout.getUserId();
        for (Checkout tempCheckout : checkoutData) {
            if (tempCheckout.getUserId().equals(userId)) {
                int index = checkoutData.indexOf(tempCheckout);
                checkoutData.remove(index);
                checkoutData.add(index, checkout);
                return tempCheckout;
            }
        }
        return null;
    }

    public Checkout findById(String userId) {
        for (Checkout checkout : checkoutData) {
            if (checkout.getUserId().equals(userId)) {
                return checkout;
            }
        }
        throw new NoSuchElementException("Checkout not found");
    }

    public void delete(String userId) {
        Checkout toDelete = findById(userId);
        if (toDelete != null) {
            checkoutData.remove(toDelete);
        } else {
            throw new NoSuchElementException("Checkout not found");
        }
    }
}
