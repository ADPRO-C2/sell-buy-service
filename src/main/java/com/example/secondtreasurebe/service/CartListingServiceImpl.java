package com.example.secondtreasurebe.service;

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
    public CartListing updateCartListing(CartListing cartListing) {
        CartListing cartListing1 = cartListingRepository.update(cartListing);
        if (cartListing1 == null) {
            throw new NoSuchElementException("CartListing not found.");
        }
        return cartListing1;
    }

    @Override
    public void deleteCartListing(String cartListingId) {
        if (cartListingRepository.findById(cartListingId) == null) {
            throw new NoSuchElementException("CartListing not found.");
        } else {
            cartListingRepository.delete(cartListingId);
        }
    }

    @Override
    public CartListing findById(String cartListingId) {
        CartListing cartListing = cartListingRepository.findById(cartListingId);
        if (cartListing == null) {
            throw new NoSuchElementException("CartListing not found.");
        }
        return cartListing;
    }


    @Override
    public List<CartListing> findAllCartListings() {
        return cartListingRepository.findAll();
    }
}
