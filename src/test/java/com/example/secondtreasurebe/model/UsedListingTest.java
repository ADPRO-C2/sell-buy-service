package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsedListingTest {
    UsedListing listing;
    @BeforeEach
    void setUp() {
        this.listing = new UsedListing();
        this.listing.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        this.listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.listing.setName("Kemeja Linen Blend");
        this.listing.setStock(10);
        this.listing.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        this.listing.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        this.listing.setPrice(299000);
        this.listing.setRateCondition(2);
    }

    @Test
    void getListingId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.listing.getListingId());
    }

    @Test
    void getUserId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", this.listing.getUserId());
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
        assertEquals(299000, this.listing.getPrice());
    }

    @Test
    void getRateCondition() {
        assertEquals(2, this.listing.getRateCondition());
    }

    @Test
    public void getType() {
        UsedListing usedListing = new UsedListing();
        String type = usedListing.getType();
        assertEquals("used", type);
    }
}