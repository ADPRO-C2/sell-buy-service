package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return orderRepository.update(order);
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderRepository.findById(orderId);
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
