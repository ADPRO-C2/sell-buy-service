package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        orderData.add(order);
        return order;
    }

    public Order update(Order order) {
        String id = order.getOrderId();
        for (Order tempOrder : orderData) {
            if (tempOrder.getOrderId().equals(id)) {
                int i = orderData.indexOf(tempOrder);
                orderData.remove(i);
                orderData.add(i, order);
                return tempOrder;
            }
        }
        return null;
    }

    public Order findById(String orderId) {
        for (Order order : orderData) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void delete(String orderId) {
        Order toDelete = findById(orderId);
        if (toDelete != null) {
            orderData.remove(toDelete);
        }
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderData);
    }

    public List<Order> findAllUserOrders(String userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orderData) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
}
