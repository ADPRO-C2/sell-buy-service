package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.repository.CartListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class CartListingServiceImpl implements CartListingService {

    @Autowired
    private CartListingRepository cartListingRepository;

    @Override
    public CartListing createCartListing(CartListing cartListing) {
        return cartListingRepository.save(cartListing);
    }

    @Override
    public CartListing updateAmount(CartListing cartListing, int newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("New amount must be greater than 0.");
        }
        cartListing.setAmount(newAmount);
        return cartListingRepository.save(cartListing);
    }


    @Override
    public void deleteCartListing(String cartListingId) {
        if (!cartListingRepository.existsById(cartListingId)) {
            throw new NoSuchElementException("CartListing not found.");
        } else {
            cartListingRepository.deleteById(cartListingId);
        }
    }

    @Override
    public CartListing findById(String cartListingId) {
        return cartListingRepository.findById(cartListingId)
                .orElseThrow(() -> new NoSuchElementException("CartListing not found."));
    }
}
