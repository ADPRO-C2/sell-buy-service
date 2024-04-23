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

@Service
public class ListingServiceImpl implements ListingServiceInterface{
    @Autowired
    private ListingRepository listingRepository = new ListingRepository();
    private static final ListingFactoryImpl listingFactory = new ListingFactoryImpl();

    @Override
    public Listing createListing(ListingRequest listingRequest) {
        Listing listing = listingFactory.createListing(listingRequest.getRateCondition());
        listing.setUserId(listingRequest.getUserId());
        listing.setListingId(listingRequest.getListingId());
        listing.setName(listingRequest.getName());
        listing.setDescription(listingRequest.getDescription());
        listing.setPrice(listingRequest.getPrice());
        listing.setStock(listingRequest.getStock());
        listing.setPhotoUrl(listingRequest.getPhotoUrl());
        listing.setRateCondition(listingRequest.getRateCondition());

        listingRepository.createListing(listing);
        return listing;
    }

    public List<Listing> findAll() {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        List<Listing> allListing = new ArrayList<>();
        listingIterator.forEachRemaining(allListing::add);
        return allListing;
    }
}