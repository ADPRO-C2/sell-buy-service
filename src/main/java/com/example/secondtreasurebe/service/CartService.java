package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.CartListing;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CartService {
    public Cart createCart(Cart cart);
    public Cart findById(int userId);
    public void deleteCart(int userId);
    public List<CartListing> findAllInCart(int userId);
}
