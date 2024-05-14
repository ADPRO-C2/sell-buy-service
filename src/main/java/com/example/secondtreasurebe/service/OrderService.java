package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public Order createOrder(String userId, Order order);
    public Order updateOrderStatus(Order order, OrderStatus status);
    public Order findOrderById(String orderId);
    public void deleteOrder(String orderId);
    public List<Order> findAllOrdersFromUser(String userId);
}
