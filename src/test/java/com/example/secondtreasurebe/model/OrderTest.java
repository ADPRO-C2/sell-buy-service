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

        List<CartListing> items = new ArrayList();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();
        CartListing cartListing1 = new CartListing(listing1, 2);
        CartListing cartListing2 = new CartListing(listing2, 3);
        items.add(cartListing1);
        items.add(cartListing2);
        tempCart.setItems(items);

        this.order = new Order("eb558e9f-1c39-460e-8860-71af6af63ba7", tempCart);
        this.order.setOrderId("059680d7-9977-4828-9d86-ff6d245bc052");
        this.order.setStatus("Dikemas");
    }

    @Test
    void testEmptyOrder() {
        Cart cart = new Cart("13652556-012a-4c07-b56-54eb1396d79b");
        Order emptyOrder = new Order("13652556-012a-4c07-b56-54eb1396d79b", cart);
        assertTrue(emptyOrder.getItems().isEmpty);
    }

    @Test
    void testCreateValidOrder() {
        assertNotNull(order);

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", order.getUserId());
        assertEquals("059680d7-9977-4828-9d86-ff6d245bc052", order.getOrderId());
        assertEquals("Dikemas", order.getStatus());

        List<CartListing> items = order.getItems();
        assertEquals(2, items.size());

        // Verify item details
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();
        CartListing cartListing1 = items.get(0);
        assertEquals(listing1, cartListing1.getListing());
        assertEquals(2, cartListing1.getAmount());

        CartListing cartListing2 = items.get(1);
        assertEquals(listing2, cartListing2.getListing());
        assertEquals(3, cartListing2.getAmount());
    }

    @Test
    void testNegativePriceTotal() {
        assertThrows(IllegalArgumentException.class, () -> order.setPriceTotal(-100));
    }

    @Test
    void testValidStatus() {
        this.order.setStatus("Dikemas");
        assertEquals("Dikemas", this.order.getStatus());
    }

    @Test
    void testInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> order.setStatus("InvalidStatus"));
    }
}