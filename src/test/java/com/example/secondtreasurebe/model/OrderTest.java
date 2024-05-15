package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    Order order;

    @BeforeEach
    void setUp() {
        order = new Order("5c0803ec-54aa-405c-8f46-460176365cf3");
    }

    @Test
    void testCreateValidOrder() {
        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals("5c0803ec-54aa-405c-8f46-460176365cf3", order.getCartListingid());
        assertEquals(OrderStatus.DIKEMAS, order.getStatus());
    }

    @Test
    void testValidStatus() {
        List<OrderStatus> validStatuses = Arrays.asList(OrderStatus.DIKEMAS, OrderStatus.DI_JALAN, OrderStatus.SUDAH_SAMPAI);
        for (OrderStatus status : validStatuses) {
            assertTrue(order.isValidStatus(status));
        }
    }

    @Test
    void testInvalidStatus() {
        OrderStatus invalidStatus = null;
        assertFalse(order.isValidStatus(invalidStatus));
    }
}
