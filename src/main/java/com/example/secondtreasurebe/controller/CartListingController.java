package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.service.CartListingServiceImpl;
import com.example.secondtreasurebe.service.ListingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    public ResponseEntity<CartListing> createCartListing(@PathVariable String listingId, @RequestParam int amount, @RequestParam int userId) {
        try {
            CartListing cartListing = service.createCartListing(listingId, amount, userId);
            return new ResponseEntity<>(cartListing, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to process request: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create cart listing", e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CartListing> updateAmount(@RequestParam String cartListingId, @RequestParam int amount) {
        try {
            var res = service.updateAmount(cartListingId, amount);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to process request: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @GetMapping("/{cartListingId}")
    public ResponseEntity<CartListing> getCartListingById(@PathVariable String cartListingId) {
        try {
            var cartListing = service.findCartListingById(cartListingId);
            return new ResponseEntity<>(cartListing, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing ID " + cartListingId + " not found");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartListing>> getAllCartListingsByUserId(@PathVariable int userId) {
        try {
            var cartListings = service.findAllCartListingsByUserId(userId);
            return new ResponseEntity<>(cartListings, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
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