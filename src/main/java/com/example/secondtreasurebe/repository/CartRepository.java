package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class CartRepository {

    private List<Cart> cartData = new ArrayList<>();

    public Cart save(Cart cart) {
        cartData.add(cart);
        return cart;
    }

    public Cart update(Cart cart) {
        String id = cart.getUserId();
        for (Cart tempCart : cartData) {
            if (tempCart.getUserId().equals(id)) {
                int i = cartData.indexOf(tempCart);
                cartData.remove(i);
                cartData.add(i, tempCart);
                return tempCart;
            }
        }
        return null;
    }

    public Cart findById(String userId) {
        for (Cart cart : cartData) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }

    public void delete(String userId) {
        Cart toDelete = findById(userId);
        if (toDelete != null) {
            cartData.remove(toDelete);
        }
    }

    public List<Cart> findAll() {
        return new ArrayList<>(cartData);
    }

}
