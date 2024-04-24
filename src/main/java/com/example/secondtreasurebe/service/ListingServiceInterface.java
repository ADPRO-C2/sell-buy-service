package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.ListingRequest;

import java.util.List;

public interface ListingServiceInterface {
    public Listing createListing(ListingRequest listingRequest);
    public List<Listing> findAll();
    public Listing findListingById(String id);
    public void edit(Listing newListing);
}