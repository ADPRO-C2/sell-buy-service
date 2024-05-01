package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    // private List<CartListing> cartListings;

    @BeforeEach
    void setUp() {
        this.cartListings = new ArrayList<>();
        Cart testCart = new Cart();
        CartListing cartListing1 = new CartListing("11", 2);
        CartListing cartLisitng2 = new CartListing("22", 3);
    }

    @Test
    void testCreateEmptyCart() {
        Cart cart = new Cart("13652556-012a-4c07-b56-54eb1396d79b");
        cart.setCartId("eb558e9f-1c39-460e-8860-71af6af63ba7"); //set this one

        assertEquals(cart.getUserId(), "13652556-012a-4c07-b56-54eb1396d79b");
        assertEquals(cart.getCartId(), "eb558e9f-1c39-460e-8860-71af6af63ba7");
    }

    @Test
    void testGetUserId() {
        assertEquals(testCart.getUserId(), "eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testListingsInCart() {

        List<Listing> listingTest = new ArrayList<>();
        Listing listing1 = new Listing();
        listingTest.add(listing1);
        assertEquals(this.carts.get(1).getInCart(), listingTest);
    }
}