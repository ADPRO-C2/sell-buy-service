/*package com.example.secondtreasurebe.repository;

import org.springframework.stereotype.Repository;
import com.example.secondtreasurebe.model.Listing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CartRepository {
    private List<Cart> cartData = new ArrayList<>();

    public void addToCart(Listing listing, String userId) {
        Cart userCart = findByUserId(userId);
        List<Listing> cartListings = userCart.getInCart();
        cartListings.add(listing);
        userCart.setInCart(cartListings);
    }

    public void removeFromCart(Listing listing, String userId) {
        Cart userCart = findByUserId(userId);
        List<Listing> cartListings = userCart.getInCart();
        cartListings.remove(listing);
        userCart.setInCart(cartListings);
    }

    public Cart findByUserId(String userId) {
        for (Cart cart : cartData) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }

    public Iterator<Cart> findAll() { return cartData.iterator();}

    public List<Listing> getListing(String userId) {
        Cart userCart = findByUserId(userId);
        return userCart.getInCart();
    }
    public void itemCheckout(Listing listing, String userId) {
        Cart userCart = findByUserId(userId);
        List<Listing> cartListings = userCart.getInCart();
        List<Listing> cartCheckout = userCart.getCheckout();
        cartListings.remove(listing);
        cartCheckout.add(listing);
        userCart.setInCart(cartListings);
        userCart.setCheckout(cartCheckout);
    }
}*/