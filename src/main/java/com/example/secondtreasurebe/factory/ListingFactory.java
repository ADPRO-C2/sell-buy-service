package com.example.secondtreasurebe.factory;

import com.example.secondtreasurebe.model.Listing;

public interface ListingFactory {
    public Listing createListing(int rateCondition);
}