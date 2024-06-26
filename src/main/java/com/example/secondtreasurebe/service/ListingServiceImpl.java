package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.ListingRepository;
import com.example.secondtreasurebe.strategydp.SortByNameStrategy;
import com.example.secondtreasurebe.strategydp.SortByPriceStrategy;
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
        listing.setListingId("");
        if (listing.getListingId().isEmpty()) {
            String id = generateListingId();
            listing.setListingId(id);
        }
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

            listing.validate();

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

    @Override
    public List<Listing> getListingByUserId(int id){
        return listingRepository.findByUserId(id);
    }

    @Override
    public List<Listing> getSortedListingsByName(int id){
        List<Listing> listings = listingRepository.findByUserId(id);
        SortByNameStrategy sorter = new SortByNameStrategy();

        return sorter.sort(listings);
    }

    @Override
    public List<Listing> getSortedListingsByPrice(int id){
        List<Listing> listings = listingRepository.findByUserId(id);
        SortByPriceStrategy sorter = new SortByPriceStrategy();

        return sorter.sort(listings);
    }
}