package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartListingTest {
    CartListing cartListing;

    @BeforeEach
    void setUp() {
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Nintando Swotch");
        listing1.setDescription("The best console.");
        listing1.setPrice(100000);
        listing1.setStock(45);
        listing1.setPhotoUrl("https://images.tokopedia.net/img/cache/700/VqbcmM/2023/10/7/f588f985-f66a-4749-979c-07b971cf38e9.png.webp?ect=4g");
        listing1.setRateCondition(0);

        this.cartListing = new CartListing.Builder()
                .listing(listing1)
                .amount(3)
                .build();

        this.cartListing.setCartListingId("7766d08b-aa3b-4364-af55-62c282fd2b05");
    }

    @Test
    void testCreateValidCartListing() {
        assertEquals("7766d08b-aa3b-4364-af55-62c282fd2b05", this.cartListing.getCartListingId());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", this.cartListing.getUserId());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.cartListing.getListingId());
        assertEquals("Nintando Swotch", this.cartListing.getName());
        assertEquals("The best console.", this.cartListing.getDescription());
        assertEquals(100000, this.cartListing.getPrice());
        assertEquals(45, this.cartListing.getStock());
        assertEquals("https://images.tokopedia.net/img/cache/700/VqbcmM/2023/10/7/f588f985-f66a-4749-979c-07b971cf38e9.png.webp?ect=4g", this.cartListing.getPhotoUrl());
        assertEquals(0, this.cartListing.getRateCondition());
    }

    @Test
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new CartListing.Builder()
                        .listing(new Listing())
                        .amount(-2)
                        .build()
        );
    }

    @Test
    void testZeroAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new CartListing.Builder()
                        .listing(new Listing())
                        .amount(0)
                        .build()
        );
    }
}