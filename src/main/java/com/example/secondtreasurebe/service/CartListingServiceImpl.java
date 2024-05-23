package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CartListingServiceImpl implements CartListingService {

    @Autowired
    private CartListingRepository cartListingRepository;

    @Autowired
    private ListingServiceImpl listingService;

    @Override
    public CartListing createCartListing(String listingId, int amount) {
        if (listingId == null || listingId.isEmpty()) {
            throw new IllegalArgumentException("Listing ID cannot be null or empty.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        Listing listing = listingService.findListingById(listingId);
        if (listing == null) {
            throw new NoSuchElementException("Listing with ID " + listingId + " not found.");
        }

        BigDecimal totalPrice = listing.getPrice().multiply(BigDecimal.valueOf(amount));

        CartListing.Builder builder = new CartListing.Builder();
        builder.listingId(listingId)
                .amount(amount)
                .totalPrice(totalPrice);

        CartListing cartListing = builder.build();

        String id = UUID.randomUUID().toString();
        cartListing.setCartListingId(id);
        cartListing.setUserId(1); //manually set for now

        return cartListingRepository.save(cartListing);
    }

    @Override
    public CartListing updateAmount(String cartListingId, int newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("New amount must be greater than 0.");
        }

        CartListing cartListing = cartListingRepository.findById(cartListingId)
                .orElseThrow(() -> new NoSuchElementException("Cart listing not found"));

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
        return cartListingRepository.findAllByUserId(userId);
    }
}