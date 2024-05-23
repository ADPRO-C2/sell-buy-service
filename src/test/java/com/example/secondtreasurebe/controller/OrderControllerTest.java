package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.repository.OrderRepository;
import com.example.secondtreasurebe.service.CartListingServiceImpl;
import com.example.secondtreasurebe.service.ListingServiceImpl;
import com.example.secondtreasurebe.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController controller;

    @Mock
    private OrderServiceImpl service;

    @Mock
    private CartListingServiceImpl cartListingService;

    @Mock
    private ListingServiceImpl listingService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateOrderSuccess() {
        String expectedOrderId = "insert-order-id";
        String cartListingId = "insert-cartlisting-id";
        int expectedUserId = 1;
        int expectedAmount = 2;
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(100.0);

        CartListing mockCartListing = new CartListing();
        mockCartListing.setUserId(expectedUserId);
        mockCartListing.setListingId("insert-listing-id");
        mockCartListing.setAmount(expectedAmount);
        mockCartListing.setTotalPrice(expectedTotalPrice);

        Listing mockListing = Mockito.mock(Listing.class);
        mockListing.setPhotoUrl("https://example.com/image.jpg");
        mockListing.setName("Cool Gadget");
        mockListing.setUserId(2);
        mockListing.setPrice(BigDecimal.valueOf(50.0));

        Order mockOrder = new Order();
        mockOrder.setOrderId("insert-order-id");
        mockOrder.setUserId(expectedUserId);
        mockOrder.setSellerId(2);
        mockOrder.setAmount(2);
        mockOrder.setTotalPrice(BigDecimal.valueOf(100.00));
        mockOrder.setStatus(OrderStatus.DIKEMAS);
        mockOrder.setListingName("Cool Gadget");
        mockOrder.setPhotoUrl("https://example.com/image.jpg");
        mockOrder.setDateBought(LocalDate.now());

        Mockito.when(service.createOrder(cartListingId)).thenReturn(mockOrder);

        ResponseEntity<Order> response = controller.createOrder(cartListingId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Order createdOrder = response.getBody();

        assertNotNull(createdOrder);

        assertEquals(expectedOrderId, createdOrder.getOrderId());
        assertEquals(expectedUserId, createdOrder.getUserId());
        assertEquals(expectedAmount, createdOrder.getAmount());
        assertEquals(expectedTotalPrice, createdOrder.getTotalPrice());
        assertEquals("Cool Gadget", createdOrder.getListingName());
        assertEquals("https://example.com/image.jpg", createdOrder.getPhotoUrl());
        assertEquals(2, createdOrder.getSellerId());
        assertEquals(LocalDate.now(), createdOrder.getDateBought());
        assertEquals(OrderStatus.DIKEMAS, createdOrder.getStatus());
    }

    @Test
    public void testCreateOrder_CartListingNotFound() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.when(service.createOrder(cartListingId)).thenThrow(new NoSuchElementException("CartListing not found"));

        try {
            controller.createOrder(cartListingId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("CartListing not found"));
        }
    }

    @Test
    public void testCreateOrder_InvalidRequest() {
        String cartListingId = null;

        Mockito.when(service.createOrder(cartListingId)).thenThrow(new IllegalArgumentException("Invalid request"));

        try {
            controller.createOrder(cartListingId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            assertTrue(e.getMessage().contains("Failed to process request"));
        }
    }

    @Test
    public void testUpdateOrderStatusSuccess() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        OrderStatus newStatus = OrderStatus.DI_JALAN;

        Order expectedOrder = new Order();
        expectedOrder.setOrderId(orderId);
        expectedOrder.setStatus(newStatus);

        Mockito.when(service.updateOrderStatus(orderId, newStatus)).thenReturn(expectedOrder);

        ResponseEntity<Order> response = controller.updateOrderStatus(orderId, newStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrder, response.getBody());
    }

    @Test
    public void testUpdateOrderStatus_OrderNotFound() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        OrderStatus newStatus = OrderStatus.DI_JALAN;

        Mockito.when(service.updateOrderStatus(orderId, newStatus)).thenThrow(new NoSuchElementException("Order not found"));

        try {
            controller.updateOrderStatus(orderId, newStatus);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            assertTrue(e.getMessage().contains("Failed to process request"));
        }
    }

    @Test
    public void testGetOrderByIdSuccess() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Order expectedOrder = new Order();
        expectedOrder.setOrderId(orderId);

        Mockito.when(service.findOrderById(orderId)).thenReturn(expectedOrder);

        ResponseEntity<Order> response = controller.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrder, response.getBody());
    }

    @Test
    public void testGetOrderById_OrderNotFound() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.when(service.findOrderById(orderId)).thenThrow(new NoSuchElementException("Order ID " + orderId + " not found"));

        try {
            controller.getOrderById(orderId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("Order ID " + orderId + " not found"));
        }
    }

    @Test
    public void testDeleteOrderSuccess() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.doNothing().when(service).deleteOrder(orderId);

        ResponseEntity<String> response = controller.deleteOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteOrder_OrderNotFound() {
        String orderId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.doThrow(new NoSuchElementException("Order not found")).when(service).deleteOrder(orderId);

        try {
            controller.deleteOrder(orderId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("Order not found"));
        }
    }

    @Test
    public void testGetAllOrdersByUserIdSuccess() {
        int userId = 1;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());

        Mockito.when(service.findAllOrdersByUserId(userId)).thenReturn(expectedOrders);

        ResponseEntity<List<Order>> response = controller.getAllOrdersByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrders, response.getBody());
    }
}