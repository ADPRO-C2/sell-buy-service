package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(int userId, Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Order order, OrderStatus status) {
        Order existingOrder = orderRepository.findById(order.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Order not found for ID: " + order.getOrderId()));

        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found."));
    }

    @Override
    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NoSuchElementException("Order not found.");
        } else {
            orderRepository.deleteById(orderId);
        }
    }

    @Override
    public List<Order> findAllOrdersByUserId(int userId) {
        if (!orderRepository.existsById(String.valueOf(userId))) {
            throw new NoSuchElementException("User not found.");
        } else {
            return orderRepository.findAllByUserId(userId);
        }
    }
}