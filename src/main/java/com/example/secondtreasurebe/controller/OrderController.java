package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.dto.CreateOrderRequest;
import com.example.secondtreasurebe.dto.UpdateOrderStatusRequest;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Order;
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
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    OrderServiceImpl service;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            service.createOrder(request.getUserId(), request.getOrder());
            return new ResponseEntity<>(request.getOrder(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Order> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        try {
            var order = service.updateOrderStatus(request.getOrder(), request.getStatus());
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

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersByUserId(@RequestBody int userId) {
        List<Order> orders = service.findAllOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
