package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CartService {
    public Cart createCart(Cart cart);
    public Cart findById(String userId);
    public void deleteCart(String userId);
    public List<Cart> findAllCarts();
}
