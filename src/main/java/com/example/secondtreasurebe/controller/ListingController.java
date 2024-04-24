package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.ListingRequest;
import com.example.secondtreasurebe.service.ListingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/api/sell")
public class ListingController {

    @Autowired
    private ListingServiceInterface service;

    @GetMapping("/create")
    public String createListingPage(Model model) {
        ListingRequest listingRequest= new ListingRequest();
        model.addAttribute("listingRequest", listingRequest);
        return "CreateListing";
    }

    @PostMapping("/create-listing")
    public ResponseEntity<Listing> createListing(@RequestBody ListingRequest listingRequest) {
        Listing createdListing = service.createListing(listingRequest);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @GetMapping("/listings")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> allListings = service.findAll();
        return new ResponseEntity<>(allListings, HttpStatus.OK);
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable String id) {
        Listing listing = service.findListingById(id);
        if (listing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listing, HttpStatus.OK);
        }
    }
}
