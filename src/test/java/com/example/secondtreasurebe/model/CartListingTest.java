package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartListingTest {
    CartListing cartListing;

    @BeforeEach
    void setUp() {
        this.cartListing = new CartListing("eb558e9f-1c39-460e-8860-71af6af63bd6", 3);
        this.cartListing.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        this.cartListing.setName("Nintando Swotch");
        this.cartListing.setDescription("The best console.");
        this.cartListing.setPrice(100000);
        this.cartListing.setStock(45);
        this.cartListing.setPhotoUrl("https://images.tokopedia.net/img/cache/700/VqbcmM/2023/10/7/f588f985-f66a-4749-979c-07b971cf38e9.png.webp?ect=4g");
        this.cartListing.setRateCondition(0);
    }

    @Test
    void testCreateValidCartListing() {
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
        assertThrows(IllegalArgumentException.class, cartListing::validateAmount);

    }

    @Test
    void testZeroAmount() {
        this.cartListing.setAmount(0);
        assertThrows(IllegalArgumentException.class, cartListing::validateAmount);
    }
}
