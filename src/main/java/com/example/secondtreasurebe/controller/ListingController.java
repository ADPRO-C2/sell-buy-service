package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.service.ListingServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ListingController {

    @Autowired
    private ListingServiceInterface service;

    @PostMapping("/listing/create")
    public ResponseEntity<Listing> createListing(@Valid @RequestBody Listing listing, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            service.createListing(listing);
            return new ResponseEntity<>(listing, HttpStatus.CREATED);
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
    public ResponseEntity<Listing> editListing(@RequestBody Listing listing, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            if (listing.getListingId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Listing ID is required for editing");
            }
            var res = service.edit(listing);
            return new ResponseEntity<>(res, HttpStatus.OK);
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
}
