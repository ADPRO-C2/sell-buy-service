package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class CartListingRepository {
    private List<CartListing> cartListingData = new ArrayList<>();

    public CartListing save(CartListing cartListing) {
        cartListingData.add(cartListing);
        return cartListing;
    }

    public CartListing update(CartListing cartListing) {
        String id = cartListing.getCartListingId();
        for (CartListing cListing : cartListingData) {
            if (cListing.getCartListingId().equals(id)) {
                int i = cartListingData.indexOf(cListing);
                cartListingData.remove(i);
                cartListingData.add(i, cartListing);
                return cListing;
            }
        }
        return null;
    }

    public CartListing findById(String cartListingId) {
        for (CartListing cListing : cartListingData) {
            if (cListing.getCartListingId().equals(cartListingId)) {
                return cListing;
            }
        }
        return null;
    }

    public void delete(String cartListingId) {
        CartListing toDelete = findById(cartListingId);
        if (toDelete != null) {
            cartListingData.remove(toDelete);
        }
    }

    public List<CartListing> findAll() {
        return new ArrayList<>(cartListingData);
    }
}
