package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart testCart;

    @BeforeEach
    void setUp() {
        this.testCart = new Cart(11);

        Listing listing = new Listing();
        listing.setUserId(12);
        listing.setListingId("listing-id-456");
        listing.setName("Sample Listing");
        listing.setDescription("This is a sample listing.");
        listing.setPrice(1000);
        listing.setStock(10);
        listing.setPhotoUrl("https://example.com/sample.jpg");
        listing.setRateCondition(4);

        CartListing cartListing = new CartListing.Builder()
                .listing(listing)
                .amount(2)
                .build();
        cartListing.setCartListingId("cart-listing-id");
        this.testCart.addCartListing(cartListing);
    }

    @Test
    void testEmptyCart() {
        Cart cart = new Cart(13);
        List<CartListing> empty = new ArrayList<>();
        cart.setItems(empty);

        assertEquals(13, cart.getUserId());
    }


    @Test
    void testAddToCart() {
        assertEquals("cart-listing-id", this.testCart.getItems().get(0).getCartListingId());
        assertEquals(2, this.testCart.getItems().get(0).getAmount());
    }

    @Test
    void testRemoveFromCart() {
        CartListing toDelete = this.testCart.getItems().get(0);
        this.testCart.removeCartListing(toDelete);

        assertTrue(this.testCart.getItems().isEmpty());
    }

    @Test
    void testUpdate() {
        Cart cart = new Cart(15);

        CartListing listingToUpdate = new CartListing.Builder()
                .listing(new Listing())
                .amount(3)
                .build();
        cart.addCartListing(listingToUpdate);

        CartListing retrievedListing = cart.getItems().get(0);
        retrievedListing.setAmount(5);

        cart.editCartListing(retrievedListing, 5);

        assertEquals(5, cart.getItems().get(0).getAmount());
    }
}