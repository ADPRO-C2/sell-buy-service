package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CartListingService {
    public CartListing createCartListing(String listingId, int amount);
    public CartListing updateAmount(String cartListingId, int newAmount);
    public void deleteCartListing(String cartListingId);
    public CartListing findCartListingById(String cartListingId);
    public List<CartListing> findAllCartListingsByUserId(int userId);
}
