package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public Order createOrder(String userId, Order order);
    public Order findOrderById(String orderId);
    public void deleteOrder(String orderId);
    public List<Order> findAllOrders();
    public List<Order> findAllOrdersFromUser(String userId);
}
