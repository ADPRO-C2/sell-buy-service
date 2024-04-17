package com.example.secondtreasurebe.factory;

import org.junit.jupiter.api.Test;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.NewListing;
import com.example.secondtreasurebe.model.UsedListing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListingFactoryImplTest {
    @Test
    public void testCreateListingWithInvalidRateCondition() {
        ListingFactoryImpl factory = new ListingFactoryImpl();
        assertThrows(IllegalArgumentException.class, () -> {
            Listing listing = factory.createListing(-1);
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
