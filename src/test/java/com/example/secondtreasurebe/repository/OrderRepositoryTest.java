package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @InjectMocks
    OrderRepository orderRepository;

    List<Order> orderList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Cart cart = new Cart("c56c2def-f1d7-4462-91c2-6f2dfaf08380");
        List<CartListing> items = new ArrayList<>();
        items.add(new CartListing.Builder()
                .listing(new Listing())
                .amount(1)
                .build());
        items.add(new CartListing.Builder()
                .listing(new Listing())
                .amount(2)
                .build());
        cart.setItems(items);
        Order order = new Order("c56c2def-f1d7-4462-91c2-6f2dfaf08380", cart);
        order.setOrderId("4a90b156-04a2-48b2-abb5-70976cb01d79");
        orderList.add(order);
    }

    @AfterEach
    void tearDown() {
        orderList.clear();
    }

    @Test
    void testSaveOrder() {
        Order order = orderList.get(0);
        orderRepository.save(order);

        Order findSavedOrder = orderRepository.findById(order.getOrderId());
        assertEquals(order.getUserId(), findSavedOrder.getUserId());
        assertEquals(order.getItems(), findSavedOrder.getItems());
    }

    @Test
    void testUpdateOrder() {
        Order order = orderList.get(0);
        orderRepository.save(order);

        Order updatedOrder = orderRepository.findById(order.getOrderId());
        List<CartListing> updatedItems = new ArrayList<>();
        updatedItems.add(new CartListing.Builder()
                .listing(new Listing())
                .amount(3)
                .build());
        updatedOrder.setItems(updatedItems);

        orderRepository.update(updatedOrder);
        Order check = orderRepository.findById(order.getOrderId());
        assertEquals(updatedOrder.getItems(), check.getItems());
    }

    @Test
    void testDeleteOrder() {
        Order order = orderList.get(0);
        orderRepository.save(order);
        Order findSavedOrder = orderRepository.findById(order.getOrderId());

        assertNotNull(findSavedOrder);
        orderRepository.delete(order.getOrderId());
        findSavedOrder = orderRepository.findById(order.getOrderId());

        assertNull(findSavedOrder);
    }

    @Test
    void testFindById() {
        Order order2 = new Order("d19c7d53-a48e-485e-a824-e5a1c99d6e27", new Cart("d19c7d53-a48e-485e-a824-e5a1c99d6e27"));
        order2.setOrderId("b1c7c39f-1426-449e-a6fc-17db9ba7076e");
        orderRepository.save(order2);

        Order check = orderList.get(0);
        orderRepository.save(check);
        Order findOrder = orderRepository.findById(check.getOrderId());

        assertEquals(check.getUserId(), findOrder.getUserId());
        assertEquals(check.getItems(), findOrder.getItems());
    }

    @Test
    void testFindAllOrders() {
        Order order2 = new Order("d19c7d53-a48e-485e-a824-e5a1c99d6e27", new Cart("d19c7d53-a48e-485e-a824-e5a1c99d6e27"));
        Order order3 = new Order("50618969-f5a0-445a-b339-faea5c071b88", new Cart("50618969-f5a0-445a-b339-faea5c071b88"));
        orderList.add(order2);
        orderList.add(order3);

        for (Order order : orderList) {
            orderRepository.save(order);
        }

        List<Order> allOrders = orderRepository.findAll();
        assertEquals(3, allOrders.size());
    }

    @Test
    void testFindAllOrderFromUser() {
        Order order1 = new Order("61598f17-bfb8-4e15-a2d9-af6030be590a", new Cart("61598f17-bfb8-4e15-a2d9-af6030be590a"));
        Order order2 = new Order("c8c3bc59-82ab-4ecc-8396-7a1e1d9c95a2", new Cart("c8c3bc59-82ab-4ecc-8396-7a1e1d9c95a2"));
        orderRepository.save(order1);
        orderRepository.save(order2);

        for (int i = 0; i < 3; i++) {
            Order userOrder = new Order("61598f17-bfb8-4e15-a2d9-af6030be590a", new Cart("61598f17-bfb8-4e15-a2d9-af6030be590a"));
            orderRepository.save(userOrder);
        }

        List<Order> userOrders = orderRepository.findAllUserOrders("61598f17-bfb8-4e15-a2d9-af6030be590a");

        assertEquals(4, userOrders.size());

        for (Order order : userOrders) {
            assertEquals("61598f17-bfb8-4e15-a2d9-af6030be590a", order.getUserId());
        }
    }
}