package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CartListingService {
    public CartListing createCartListing(CartListing cartListing);
    public CartListing updateAmount(CartListing cartListing, int newAmount);
    public void deleteCartListing(String cartListingId);
    public CartListing findById(String cartListingId);
}
