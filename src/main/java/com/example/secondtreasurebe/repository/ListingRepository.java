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
}
