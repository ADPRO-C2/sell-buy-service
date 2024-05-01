package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @BeforeEach
    void setUp() {
        Cart testCart = new Cart();
        testCart.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
    }

    @Test
    void testEmptyCart() {
        Cart cart = new Cart();
        List<CartListing> empty = new ArrayList<>();
        cart.setUserId("13652556-012a-4c07-b56-54eb1396d79b");
        cart.setItems(empty);

        assertEquals(cart.getUserId(), "13652556-012a-4c07-b56-54eb1396d79b");
        assertEquals(cart.getCartId(), "eb558e9f-1c39-460e-8860-71af6af63ba7");
    }

    @Test
    void testGetUserId() {
        assertEquals(testCart.getUserId(), "eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testListingsInCart() {
        List<CartListing> items = new ArrayList();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();
        CartListing cartListing1 = new CartListing(listing1, 2);
        CartListing cartListing2 = new CartListing(listing2, 3);
        items.add(cartListing1);
        items.add(cartListing2);
        testCart.setItems(items);
    }
}