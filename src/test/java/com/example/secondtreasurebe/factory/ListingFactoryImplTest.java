package com.example.secondtreasurebe.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListingFactoryImplTest {
    @Test
    public void testCreateListingWithInvalidRateCondition() {
        ListingFactoryImpl factory = new ListingFactoryImpl();
        int invalidRateCondition = -1;
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createListing(invalidRateCondition);
        });
    }

    @Test
    public void testCreateNewListingWithZeroRateCondition() {
        ListingFactoryImpl factory = new ListingFactoryImpl();
        int zeroRateCondition = 0;
        Listing listing = factory.createListing(zeroRateCondition);
        assertTrue(listing instanceof NewListing);
    }

    @Test
    public void testCreateUsedListingWithPositiveRateCondition() {
        ListingFactoryImpl factory = new ListingFactoryImpl();
        int positiveRateCondition = 1;
        Listing listing = factory.createListing(positiveRateCondition);
        assertTrue(listing instanceof UsedListing);
    }
}
