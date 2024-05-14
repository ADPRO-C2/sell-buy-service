package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {
    Checkout testCheckout;
    Cart cart;

    @BeforeEach
    void setUp() {
        this.testCheckout = new Checkout(11);
        this.cart = new Cart(11);
    }

    @Test
    void testEmptyCheckout() {
        assertTrue(this.testCheckout.getOrders().isEmpty());
    }

    @Test
    void testValidCheckout() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(this.cart);
        orderList.add(order);
        this.testCheckout.getOrders().add(order);
        assertEquals(orderList, this.testCheckout.getOrders());
    }

    @Test
    void testGetUserId() {
        assertEquals(11, this.testCheckout.getUserId());
    }

    @Test
    void testDifferentUserId() {
        Order differentUser = new Order(new Cart(12));
        this.testCheckout.getOrders().add(differentUser);
        assertThrows(IllegalArgumentException.class, this.testCheckout::sameUser);
    }
}