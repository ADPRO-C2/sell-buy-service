package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;

import java.util.List;

public interface ListingServiceInterface {
    public Listing createListing(Listing listing);
    public List<Listing> findAll();
    public Listing findListingById(String id);
    public Listing edit(Listing newListing);
    public void deleteListing(String id);
    public List<Listing> getListingByUserId(int id);
    public List<Listing> getSortedListingsByName(List<Listing> listings);
    public List<Listing> getSortedListingsByPrice(List<Listing> listings);
}