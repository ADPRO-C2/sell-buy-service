package com.example.secondtreasurebe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    Order order;

    @BeforeEach
    void setUp() {
        Cart tempCart = new Cart("dbb3d5d2-e3ab-43ee-93fe-0102f60cca35");

        List<CartListing> items = new ArrayList<>();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();

        CartListing cartListing1 = new CartListing.Builder()
                .listing(listing1)
                .amount(2)
                .build();
        CartListing cartListing2 = new CartListing.Builder()
                .listing(listing2)
                .amount(3)
                .build();
        items.add(cartListing1);
        items.add(cartListing2);
        tempCart.setItems(items);

        order = new Order(tempCart);
        order.setOrderId("059680d7-9977-4828-9d86-ff6d245bc052");
        order.setStatus("Dikemas");
    }

    @Test
    void testEmptyOrder() {
        Cart cart = new Cart("13652556-012a-4c07-b56-54eb1396d79b");
        Order emptyOrder = new Order(cart);
        assertTrue(emptyOrder.getItems().isEmpty());
    }

    @Test
    void testCreateValidOrder() {
        assertNotNull(order);

        assertEquals("dbb3d5d2-e3ab-43ee-93fe-0102f60cca35", order.getUserId());
        assertEquals("059680d7-9977-4828-9d86-ff6d245bc052", order.getOrderId());
        assertEquals("Dikemas", order.getStatus());

        List<CartListing> items = order.getItems();
        assertEquals(2, items.size());

        Listing listing1 = new Listing();
        Listing listing2 = new Listing();

        CartListing cartListing1 = items.get(0);
        assertEquals(listing1.getUserId(), cartListing1.getListing().getUserId());
        assertEquals(listing1.getListingId(), cartListing1.getListing().getListingId());

        CartListing cartListing2 = items.get(1);
        assertEquals(listing2.getUserId(), cartListing2.getListing().getUserId());
        assertEquals(listing2.getListingId(), cartListing2.getListing().getListingId());
    }

    @Test
    void testNegativePriceTotal() {
        this.order.setPriceTotal(-100);
        assertThrows(IllegalArgumentException.class, this.order::validateOrder);
    }

    @Test
    void testValidStatus() {
        this.order.setStatus("Dikemas");
        assertEquals("Dikemas", this.order.getStatus());
    }

    @Test
    void testInvalidStatus() {
        this.order.setStatus("asfdksjlf");
        assertThrows(IllegalArgumentException.class, this.order::validateOrder);
    }
}
