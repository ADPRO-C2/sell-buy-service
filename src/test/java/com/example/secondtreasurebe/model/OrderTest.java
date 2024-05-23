package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    Order order;

    @BeforeEach
    public void setUp() {
        this.order = new Order();
        this.order.setOrderId("insert-order-id");
        this.order.setUserId(1);
        this.order.setSellerId(2);
        this.order.setStatus(OrderStatus.DIKEMAS);
        this.order.setListingName("nintendo swotch");
        this.order.setAmount(3);
        this.order.setTotalPrice(BigDecimal.valueOf(100.00));
        this.order.setPhotoUrl("https://example.com/gadget.jpg");
        this.order.setDateBought(LocalDate.now());
    }
    @Test
    public void testValidOrderCreation() {
        assertEquals("insert-order-id", this.order.getOrderId());
        assertEquals(1, this.order.getUserId());
        assertEquals(2, this.order.getSellerId());
        assertEquals(OrderStatus.DIKEMAS, this.order.getStatus());
        assertEquals("nintendo swotch", this.order.getListingName());
        assertEquals(3, this.order.getAmount());
        assertEquals(BigDecimal.valueOf(100.00), this.order.getTotalPrice());
        assertEquals("https://example.com/gadget.jpg", this.order.getPhotoUrl());
        assertEquals(LocalDate.now(), this.order.getDateBought());
    }

    @Test
    public void testInvalidStatus() {
        String invalidStatus = "Invalid Status";

        assertThrows(IllegalArgumentException.class, () -> this.order.setStatus(OrderStatus.valueOf(invalidStatus)));
    }
}