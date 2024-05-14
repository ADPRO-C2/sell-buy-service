package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findById(int userId) {

        return cartRepository.findById(String.valueOf(userId)).orElseThrow(() -> new NoSuchElementException("Cart not found."));
    }

    @Override
    public void deleteCart(int userId) {
        if (!cartRepository.existsById(String.valueOf(userId))) {
            throw new NoSuchElementException("Cart not found.");
        } else {
            cartRepository.deleteById(String.valueOf(userId));
        }
    }

    @Override
    public List<CartListing> findAllInCart(int userId) {
        Cart cart = findById(userId);

        if (cart != null) {
            return cart.getItems();
        } else {
            throw new NoSuchElementException("Cart not found for user with ID: " + userId);
        }
    }
}
