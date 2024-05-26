package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.secondtreasurebe.service.OrderServiceImpl;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    OrderServiceImpl service;

    @PostMapping("/create/{cartListingId}")
    public ResponseEntity<Order> createOrder(@PathVariable String cartListingId) {
        try {
            Order order = service.createOrder(cartListingId);

            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to process request: " + e.getMessage());
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        try {
            var order = service.updateOrderStatus(orderId, status);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process request: " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        try {
            var order = service.findOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order ID " + orderId + " not found");
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        try {
            service.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersByUserId(@PathVariable int userId) {
        List<Order> orders = service.findAllOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Order>> getAllOrdersBySellerId(@PathVariable int sellerId) {
        List<Order> orders = service.findAllOrdersBySellerId(sellerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}