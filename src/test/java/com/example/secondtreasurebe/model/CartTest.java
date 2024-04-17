package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private List<Cart> carts;

    @BeforeEach
    void setUp() {
        this.carts = new ArrayList<>();
        Cart testCart = new Cart();
        List<Listing> listings = new ArrayList<>();
        List<Listing> checkout = new ArrayList<>();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();
        listings.add(listing1);
        checkout.add(listing2);
        testCart.setUserId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        testCart.setInCart(listings);
        testCart.setCheckout(checkout);
        this.carts.add(testCart);
    }

    @Test
    void testCreateEmptyCart() {
        List<Listing> noListings = new ArrayList<>();
        List<Listing> emptyCheckout = new ArrayList<>();
        Cart cart = new Cart("13652556-012a-4c07-b56-54eb1396d79b", noListings, emptyCheckout);

        assertEquals(cart.getUserId(), "13652556-012a-4c07-b56-54eb1396d79b");
        assertEquals(cart.getInCart(), noListings);
        assertEquals(cart.getCheckout(), emptyCheckout);
    }

    @Test
    void testGetUserId() {
        assertEquals(this.carts.get(1).getUserId(), "eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testListingsInCart() {
        List<Listing> listingTest = new ArrayList<>();
        Listing listing1 = new Listing();
        listingTest.add(listing1);
        assertEquals(this.carts.get(1).getInCart(), listingTest);
    }

    @Test
    void testCheckout() {
        List<Listing> checkoutTest = new ArrayList<>();
        Listing listing2 = new Listing();
        checkoutTest.add(listing2)
        assertEquals(this.carts.get(1).getCheckout(), checkoutTest);
    }
}
