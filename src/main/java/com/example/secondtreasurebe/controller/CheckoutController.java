package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.service.CheckoutServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutController {

    @Autowired
    CheckoutServiceImpl service;

    @PostMapping
    public ResponseEntity<Checkout> createCheckout(@RequestBody Checkout checkout) {
        try {
            service.createCheckout(checkout);
            return new ResponseEntity<>(checkout, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Checkout> getCheckoutById(@RequestBody int userId) {
        try {
            var checkout = service.findCheckoutById(userId);
            return new ResponseEntity<>(checkout, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID " + userId + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCheckout(@RequestBody int userId) {
        try {
            service.deleteCheckout(userId);
            return ResponseEntity.ok("Checkout deleted successfully");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checkout not found");
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllInCheckout(@RequestBody int userId) {
        var checkout = service.findCheckoutById(userId);
        List<Order> items = service.findAllInCheckout(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
