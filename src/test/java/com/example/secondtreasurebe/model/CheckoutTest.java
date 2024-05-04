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
        this.testCheckout = new Checkout("eb558e9f-1c39-460e-8860-71af6af63ba7");
        this.cart = new Cart("eb558e9f-1c39-460e-8860-71af6af63ba7");
    }

    @Test
    void testEmptyCheckout() {
        assertTrue(this.testCheckout.getOrders().isEmpty());
    }

    @Test
    void testValidCheckout() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order("eb558e9f-1c39-460e-8860-71af6af63ba7", this.cart);
        orderList.add(order);
        this.testCheckout.getOrders().add(order);
        assertEquals(orderList, this.testCheckout.getOrders());
    }

    @Test
    void testGetUserId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", this.testCheckout.getUserId());
    }

    @Test
    void testDifferentUserId() {
        Order differentUser = new Order("9c45ce81-34b9-4699-bd7d-4d8d08620de8", this.cart);
        this.testCheckout.getOrders().add(differentUser);
        assertThrows(IllegalArgumentException.class, () -> this.testCheckout.getOrders().add(differentUser));
    }
}