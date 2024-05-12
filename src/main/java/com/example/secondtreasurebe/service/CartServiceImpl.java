package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart) { return cartRepository.update(cart); }

    @Override
    public Cart findById(String userId) {
        return cartRepository.findById(userId);
    }

    @Override
    public void deleteCart(String userId) {
        cartRepository.delete(userId);
    }

    @Override
    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }
}
