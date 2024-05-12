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
    public Cart updateCart(Cart cart) {
        Cart check = cartRepository.update(cart);
        if (check != null) {
            return check;
        } else {
            throw new NoSuchElementException("Cart not found.");
        }
    }

    @Override
    public Cart findById(String userId) {
        Cart check = cartRepository.findById(userId);
        if (check != null) {
            return check;
        } else {
            throw new NoSuchElementException("Cart not found.");
        }
    }

    @Override
    public void deleteCart(String userId) {
        if (cartRepository.findById(userId) != null) {
            cartRepository.delete(userId);
        } else {
            throw new NoSuchElementException("Cart not found.");
        }
    }

    @Override
    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }
}
