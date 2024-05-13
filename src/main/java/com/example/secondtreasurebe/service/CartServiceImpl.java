package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
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
    public Cart findById(String userId) {
        return cartRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found."));
    }

    @Override
    public void deleteCart(String userId) {
        if (!cartRepository.existsById(userId)) {
            throw new NoSuchElementException("Cart not found.");
        } else {
            cartRepository.deleteById(userId);
        }
    }

    @Override
    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }
}
