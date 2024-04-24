package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Listing;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ListingRepository {
    private List<Listing> listings = new ArrayList<>();

    public Listing createListing(Listing listing) {
        listings.add(listing);
        return listing;
    }

    public Iterator<Listing> findAll() {
        return listings.iterator();
    }

    public void edit(Listing editedListing) {
        for (Listing listing : listings) {
            if (listing.getListingId().equals(editedListing.getListingId())) {
                listing.setName(editedListing.getName());
                listing.setDescription(editedListing.getDescription());
                listing.setPrice(editedListing.getPrice());
                listing.setStock(editedListing.getStock());
                listing.setPhotoUrl(editedListing.getPhotoUrl());
                listing.setRateCondition(editedListing.getRateCondition());
            }
        }
    }
}
