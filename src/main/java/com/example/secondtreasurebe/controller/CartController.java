package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    CartServiceImpl service;

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        try {
            service.createCart(cart);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Cart> findCartById(@RequestBody String userId) {
        try {
            var cart = service.findById(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartListing ID " + userId + " not found");
        }
    }

    @GetMapping
    public ResponseEntity<List<CartListing>> findAllInCart(@RequestBody String userId) {
        var cart = service.findById(userId);
        List<CartListing> items = service.findAllInCart(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCart(@RequestBody String userId) {
        try {
            service.deleteCart(userId);
            return ResponseEntity.ok("Cart deleted successfully");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
    }
}
