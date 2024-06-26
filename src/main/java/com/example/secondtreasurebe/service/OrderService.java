package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public Order createOrder(String cartListingId);
    public Order updateOrderStatus(String orderId, OrderStatus status);
    public Order findOrderById(String orderId);
    public void deleteOrder(String orderId);
    public List<Order> findAllOrdersByUserId(int userId);
    public List<Order> findAllOrdersBySellerId(int sellerId);
}