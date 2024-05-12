package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.repository.CartRepository;
import com.example.secondtreasurebe.repository.CheckoutRepository;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(CartService cartService, CheckoutService checkoutService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.checkoutService = checkoutService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        Order order1 = orderRepository.update(order);
        if (order1 == null) {
            throw new NoSuchElementException("Order not found.");
        }
        return order1;
    }

    @Override
    public Order findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NoSuchElementException("Order not found.");
        }
        return order;
    }

    @Override
    public void deleteOrder(String orderId) {
        if (orderRepository.findById(orderId) != null) {
            orderRepository.delete(orderId);
        } else {
            throw new NoSuchElementException("Order not found.");
        }
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllOrdersFromUser(String userId) {
        return orderRepository.findAllUserOrders(userId);
    }

    @Override
    public void placeOrder(String userId, Order order) {
        Cart cart = cartService.findById(userId);
        if (cart != null && !cart.getItems().isEmpty()) {
            order.setUserId(userId);

            order.setItems(cart.getItems());

            Checkout checkout = checkoutService.findCheckoutById(userId);
            if (checkout == null) {
                checkout = new Checkout(userId);
                checkout.setUserId(userId);
            }

            checkout.setUserId(userId);
            checkout.addToOrders(order);

            checkoutService.createCheckout(checkout);

            cart.setItems(new ArrayList<>());
            cartService.updateCart(cart);
        } else {
            throw new IllegalStateException("Cannot place order: Cart is empty");
        }
    }
}
