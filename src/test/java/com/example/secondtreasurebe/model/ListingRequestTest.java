package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListingRequestTest {
    ListingRequest listing;
    @BeforeEach
    void setUp() {
        this.listing = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        this.listing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.listing.setName("Kemeja Linen Blend");
        this.listing.setStock(10);
        this.listing.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        this.listing.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        this.listing.setPrice(299000);
        this.listing.setRateCondition(3);
    }

    @Test
    void testCreateListingRequestWithValidField() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", this.listing.getUserId());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.listing.getListingId());
        assertEquals("Kemeja Linen Blend", this.listing.getName());
        assertEquals(10, this.listing.getStock());
        assertEquals("Kerah terbuka, bahan nyaman dipakai.", this.listing.getDescription());
        assertEquals("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750", this.listing.getPhotoUrl());
        assertEquals(299000, this.listing.getPrice());
        assertEquals(3, this.listing.getRateCondition());
    }

    @Test
    public void testNegativePrice() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setPrice(-100);

        assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
    }

    @Test
    public void testNegativeStock() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setStock(-100);

        assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
    }

    @Test
    public void testInvalidRateCondition() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setRateCondition(10);

        assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
    }
}