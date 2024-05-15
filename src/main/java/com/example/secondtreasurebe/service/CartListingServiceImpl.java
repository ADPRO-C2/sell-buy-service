package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartListingRepository;
import com.example.secondtreasurebe.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartListingServiceImpl implements CartListingService {

    @Autowired
    private CartListingRepository cartListingRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public CartListing createCartListing(CartListing cartListing) {
        String listingId = cartListing.getListingId();
        Listing listing = listingRepository.findById(cartListing.getListingId())
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with ID: " + listingId));

        BigDecimal totalPrice = listing.getPrice().multiply(BigDecimal.valueOf(cartListing.getAmount()));

        cartListing.setTotalPrice(totalPrice);

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
    public CartListing findCartListingById(String cartListingId) {
        return cartListingRepository.findById(cartListingId)
                .orElseThrow(() -> new NoSuchElementException("CartListing not found."));
    }

    @Override
    public List<CartListing> findAllCartListingsByUserId(int userId) {
        if (!cartListingRepository.existsById(String.valueOf(userId))) {
            throw new NoSuchElementException("User not found.");
        } else {
            return cartListingRepository.findAllByUserId(userId);
        }
    }
}