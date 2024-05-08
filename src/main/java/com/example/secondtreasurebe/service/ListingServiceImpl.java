package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ListingServiceImpl implements ListingServiceInterface{
    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Listing createListing(Listing listing) {
        if (listing.getListingId() == null) {
            String id = generateListingId();
            listing.setListingId(id);
        }

        listing.setUserId("a029189d-d5cc-4933-af55-6ed9c38e6db7");
        listing.validate();

        listingRepository.save(listing);
        return listing;
    }

    private String generateListingId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    public Listing findListingById(String id){
        for (Listing listing : findAll()) {
            if(listing.getListingId()!=null) {
                if (listing.getListingId().equals(id)) {
                    return listing;
                }
            }
        }
        return null;
    }

    @Override
    public Listing edit(Listing updateListing) {
        Listing listing = findListingById(updateListing.getListingId());
        if(listing!=null){
            listing.setName(updateListing.getName());
            listing.setStock(updateListing.getStock());
            listing.setDescription(updateListing.getDescription());
            listing.setPhotoUrl(updateListing.getPhotoUrl());
            listing.setPrice(updateListing.getPrice());
            listing.setRateCondition(updateListing.getRateCondition());
            listingRepository.save(updateListing);
            return listing;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing with ID " + updateListing.getListingId() + " not found");
        }
    }

    @Override
    public void deleteListing(String id) {
        if (listingRepository.existsById(id)) {
            listingRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("No listing found with ID: " + id);
        }
    }
}