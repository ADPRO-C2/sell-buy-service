package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListingTest {
    Listing listing;
    @BeforeEach
    void setUp() {
        this.listing = new Listing();
        this.listing.setUserId(1);
        this.listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.listing.setName("Kemeja Linen Blend");
        this.listing.setStock(10);
        this.listing.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        this.listing.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        this.listing.setPrice(BigDecimal.valueOf(299000));
        this.listing.setRateCondition(0);
    }

    @Test
    void getListingId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.listing.getListingId());
    }

    @Test
    void getUserId() {
        assertEquals(1, this.listing.getUserId());
    }

    @Test
    void getName() {
        assertEquals("Kemeja Linen Blend", this.listing.getName());
    }

    @Test
    void getStock() {
        assertEquals(10, this.listing.getStock());
    }

    @Test
    void getDescription() {
        assertEquals("Kerah terbuka, bahan nyaman dipakai.", this.listing.getDescription());
    }

    @Test
    void getPhotoUrl() {
        assertEquals("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750", this.listing.getPhotoUrl());
    }

    @Test
    void getPrice() {
        assertEquals(BigDecimal.valueOf(299000), this.listing.getPrice());
    }

    @Test
    void getRateCondition() {
        assertEquals(0, this.listing.getRateCondition());
    }

    @Test
    public void testValidateValidValues() {
        listing = new Listing();
        listing.setPrice(BigDecimal.valueOf(100));
        listing.setStock(10);
        listing.setRateCondition(1);
        listing.validate();
    }

    @Test
    public void testNegativePrice() {
        listing.setPrice(BigDecimal.valueOf(-100));
        assertThrows(IllegalArgumentException.class, listing::validate);
    }

    @Test
    public void testNegativeStock() {
        listing.setStock(-100);
        assertThrows(IllegalArgumentException.class, listing::validate);
    }

    @Test
    public void testInvalidRateCondition() {
        listing.setRateCondition(10);
        assertThrows(IllegalArgumentException.class, listing::validate);
    }
}