package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        Order order1 = orderRepository.update(order);
        if (order1 == null) {
            throw new NoSuchElementException("CartListing not found.");
        }
        return order1;
    }

    @Override
    public Order findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NoSuchElementException("CartListing not found.");
        }
        return order;
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.delete(orderId);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllOrdersFromUser(String userId) {
        return orderRepository.findAllUserOrders(userId);
    }
}
