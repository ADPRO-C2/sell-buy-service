package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.service.ListingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ListingController {

    @Autowired
    private ListingServiceInterface service;

    @PostMapping("/listing/create")
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        try {
            listing.validate();
            service.createListing(listing);
            return new ResponseEntity<>(listing, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/listings")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> allListings = service.findAll();
        return new ResponseEntity<>(allListings, HttpStatus.OK);
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable("id") String id) {
        try {
            var listing = service.findListingById(id);
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Listing " + id + " not found");
        }
    }

    @PutMapping("/listing")
    public ResponseEntity<Listing> editListing(@RequestBody Listing listing) {
        try {
            listing.validate();
            var res = service.edit(listing);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @PostMapping("/listing/report/{id}")
    public ResponseEntity<String> reportListing(@PathVariable("id") String id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject("http://34.87.41.75/staff/reported-listing/add/%s", String.class);
            return new ResponseEntity<>("The listing has been reported", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-listing/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable("id") String id) {
        try {
            service.deleteListing(id);
            return ResponseEntity.ok("Listing berhasil dihapus!");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing tidak ditemukan");
        }
    }

    @GetMapping("/seller-listings/{id}")
    private List<Listing> getListingByUserId(@PathVariable("id") int id) {
        try {
            return service.getListingByUserId(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Listing " + id + " not found");
        }
    }

    @PostMapping("/seller-listings/sorted-by-name")
    private List<Listing> getSortedListingsByName(@RequestBody List<Listing> listings) {
        try {
            return service.getSortedListingsByName(listings);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @PostMapping("/seller-listings/sorted-by-price")
    private List<Listing> getSortedListingsByPrice(@RequestBody List<Listing> listings) {
        try {
            return service.getSortedListingsByPrice(listings);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }
}
