package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.ListingRequest;
import com.example.secondtreasurebe.repository.ListingRepository;
import com.example.secondtreasurebe.factory.ListingFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ListingServiceImpl implements ListingServiceInterface{
    @Autowired
    private ListingRepository listingRepository = new ListingRepository();
    private static final ListingFactoryImpl listingFactory = new ListingFactoryImpl();

    @Override
    public Listing createListing(ListingRequest listingRequest) {
        Listing listing = listingFactory.createListing(listingRequest.getRateCondition());
        listing.setUserId(listingRequest.getUserId());

        if (listing.getListingId() == null) {
            String id = generateListingId();
            listing.setListingId(id);
        }

        listing.setName(listingRequest.getName());
        listing.setDescription(listingRequest.getDescription());
        listing.setPrice(listingRequest.getPrice());
        listing.setStock(listingRequest.getStock());
        listing.setPhotoUrl(listingRequest.getPhotoUrl());
        listing.setRateCondition(listingRequest.getRateCondition());

        listingRepository.createListing(listing);
        return listing;
    }

    private String generateListingId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public List<Listing> findAll() {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        List<Listing> allListing = new ArrayList<>();
        listingIterator.forEachRemaining(allListing::add);
        return allListing;
    }

    @Override
    public Listing findListingById(String id) {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        while (listingIterator.hasNext()) {
            Listing listing = listingIterator.next();
            if (listing.getListingId().equals(id)) {
                return listing;
            }
        }
        return null;
    }

    @Override
    public void edit(Listing newListing) {
        listingRepository.edit(newListing);
    }
}