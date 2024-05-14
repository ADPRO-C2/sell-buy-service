package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.dto.UpdateCartListingRequest;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.service.CartListingServiceImpl;
import com.example.secondtreasurebe.service.ListingServiceImpl;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cartlisting")
@CrossOrigin(origins = "http://localhost:3000")
public class CartListingController {

    @Autowired
    private CartListingServiceImpl service;

    @Autowired
    private ListingServiceImpl listingService;

    @PostMapping("/cart-listings/{listingId}")
    public ResponseEntity<CartListing> createCartListing(@PathVariable String listingId, @RequestBody CartListing cartListing) {
        try {
            Listing listing = listingService.findListingById(listingId);
            cartListing.setListing(listing);
            service.createCartListing(cartListing);
            return new ResponseEntity<>(cartListing, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create cart listing", e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CartListing> updateAmount(@RequestBody UpdateCartListingRequest request) {
        try {
            var res = service.updateAmount(request.getCartListing(), request.getAmount());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @GetMapping("/{cartListingId}")
    public ResponseEntity<CartListing> getCartListingById(@PathVariable String cartListingId) {
        try {
            var cartListing = service.findById(cartListingId);
            return new ResponseEntity<>(cartListing, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing ID " + cartListingId + " not found");
        }
    }

    @DeleteMapping("/delete/{cartListingId}")
    public ResponseEntity<String> deleteCartListing(@PathVariable String cartListingId) {
        try {
            service.deleteCartListing(cartListingId);
            return ResponseEntity.ok("CartListing deleted successfully");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing not found");
        }
    }
}
