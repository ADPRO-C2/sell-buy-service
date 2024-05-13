package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.service.CartListingServiceImpl;
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

    @PostMapping
    public ResponseEntity<CartListing> createCartListing(@RequestBody CartListing cartListing) {
        try {
            service.createCartListing(cartListing);
            return new ResponseEntity<>(cartListing, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CartListing> updateAmount(@RequestBody CartListing cartListing, int amount) {
        try {
            var res = service.updateAmount(cartListing, amount);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @GetMapping("/car")
    public ResponseEntity<CartListing> getCartListingById(@RequestBody String cartListingId) {
        try {
            var cartListing = service.findById(cartListingId);
            return new ResponseEntity<>(cartListing, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing ID " + cartListingId + " not found");
        }
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteCartListing(@RequestBody String cartListingId) {
        try {
            service.deleteCartListing(cartListingId);
            return ResponseEntity.ok("CartListing deleted successfully");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing not found");
        }
    }
}
