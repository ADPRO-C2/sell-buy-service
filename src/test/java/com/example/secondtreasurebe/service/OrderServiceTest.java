package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.*;
import com.example.secondtreasurebe.repository.ListingRepository;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    CartListing.Builder builder;

    @InjectMocks
    OrderServiceImpl service;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ListingRepository listingRepository;

    @Mock
    ListingServiceImpl listingService;

    @Mock
    CartListingServiceImpl cartListingService;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId("a006d26e-b675-4919-bfc9-4a8936af9bba");
        order1.setUserId(11);
        order1.setSellerId(11);
        orders.add(order1);
        Order order2 = new Order();
        order2.setOrderId("bbd79f8c-3d06-423e-9693-02039d31401b");
        order2.setUserId(12);
        order2.setSellerId(11);
        orders.add(order2);
    }


    @AfterEach
    void tearDown() {
        orders.clear();
    }

    @Test
    void testCreateOrder() {
        Listing listing1 = new Listing();
        listing1.setListingId("12345");
        listing1.setName("thing");
        listing1.setUserId(2);
        listing1.setPhotoUrl("https://example.com/gadget.jpg");
        listing1.setStock(10); // Setting stock

        CartListing.Builder builder = new CartListing.Builder()
                .listingId("12345")
                .amount(2)
                .userId(1)
                .totalPrice(BigDecimal.valueOf(20.00));

        CartListing cartListing = builder.build();
        cartListing.setCartListingId("12");

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(cartListing.getUserId());
        order.setAmount(cartListing.getAmount());
        order.setTotalPrice(cartListing.getTotalPrice());
        order.setListingName(listing1.getName());
        order.setPhotoUrl(listing1.getPhotoUrl());
        order.setSellerId(listing1.getUserId());
        order.setDateBought(LocalDate.now());
        order.setStatus(OrderStatus.DIKEMAS);

        // Mocking the services
        when(listingService.findListingById(listing1.getListingId())).thenReturn(listing1);
        when(cartListingService.findCartListingById(cartListing.getCartListingId())).thenReturn(cartListing);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(listingRepository.save(any(Listing.class))).thenReturn(listing1); // Mocking the save operation

        Order result = service.createOrder("12");

        // Verifying the results
        assertEquals(cartListing.getUserId(), result.getUserId());
        assertEquals(OrderStatus.DIKEMAS, result.getStatus());
        assertEquals(cartListing.getAmount(), result.getAmount());
        assertEquals(cartListing.getTotalPrice(), result.getTotalPrice());
        assertEquals(listing1.getName(), result.getListingName());
        assertEquals(listing1.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(listing1.getUserId(), result.getSellerId());
        assertEquals(LocalDate.now(), result.getDateBought());
        assertEquals(OrderStatus.DIKEMAS, result.getStatus());

        // Verifying stock update
        verify(listingService, times(1)).findListingById(listing1.getListingId()); // once in test, once in service
        assertEquals(8, listing1.getStock()); // Initial stock was 10, after order of 2, stock should be 8
    }

    @Test
    void testCreateOrder_NullOrEmptyCartListingId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createOrder(null);
        });
        assertEquals("CartListing ID cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createOrder("");
        });
        assertEquals("CartListing ID cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateOrder_InsufficientStock() {
        String cartListingId = "12345";

        CartListing cartListing = new CartListing.Builder()
                .listingId("67890")
                .amount(5)
                .userId(1)
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        Listing listing = new Listing();
        listing.setListingId("67890");
        listing.setName("Test Item");
        listing.setUserId(2);
        listing.setPhotoUrl("http://example.com/photo.jpg");
        listing.setStock(3); // Stock is less than amount in cartListing

        when(cartListingService.findCartListingById(cartListingId)).thenReturn(cartListing);
        when(listingService.findListingById("67890")).thenReturn(listing);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createOrder(cartListingId);
        });
        assertEquals("Stock Habis", exception.getMessage());
    }

    @Test
    void testUpdateOrderStatus() {
        String orderId = "a006d26e-b675-4919-bfc9-4a8936af9bba";
        Order order = orders.get(0);
        OrderStatus newStatus = OrderStatus.DI_JALAN;

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = service.updateOrderStatus(order.getOrderId(), newStatus);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
        assertEquals(newStatus, result.getStatus());
    }

    @Test
    void testUpdateOrderStatus_NullStatus() {
        String orderId = "12345";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updateOrderStatus(orderId, null);
        });

        assertEquals("OrderStatus cannot be null", exception.getMessage());
    }


    @Test
    void testFindOrderById() {
        String orderId = "a006d26e-b675-4919-bfc9-4a8936af9bba";
        Order order = orders.get(0);
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        Order result = service.findOrderById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        assertEquals(order, result);
    }

    @Test
    void testFindAllOrdersFromUser() {
        int userId = 11;
        List<Order> userOrders = new ArrayList<>();
        Order userOrder1 = orders.get(0);
        userOrders.add(userOrder1);

        when(orderRepository.findAllByUserId(userId)).thenReturn(userOrders);

        List<Order> result = service.findAllOrdersByUserId(userId);

        verify(orderRepository, times(1)).findAllByUserId(userId);
        assertEquals(userOrders.size(), result.size());
        assertTrue(result.contains(userOrder1));
    }

    @Test
    void testDeleteOrder() {
        String orderId = "a006d26e-b675-4919-bfc9-4a8936af9bba";
        Order order = new Order();
        order.setOrderId(orderId);

        when(orderRepository.save(order)).thenReturn(order);
        orderRepository.save(order);

        when(orderRepository.existsById(orderId)).thenReturn(true);

        service.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testOrderNotFound() {
        String orderId = "835b30f5-9c6e-41ff-9a74-40bf9dff51bb";
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findOrderById(orderId));
    }

    @Test
    void testDeleteOrderNotExist() {
        String nonExistentOrderId = "835b30f5-9c6e-41ff-9a74-40bf9dff51bb";

        when(orderRepository.existsById(nonExistentOrderId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> service.deleteOrder(nonExistentOrderId));

        verify(orderRepository, times(1)).existsById(nonExistentOrderId);
        verify(orderRepository, never()).deleteById(anyString());
    }

    @Test
    void testFindAllOrdersFromSeller() {
        int sellerId = 11;
        List<Order> sellerOrders = new ArrayList<>();
        Order Order1 = orders.get(0);
        Order Order2 = orders.get(1);
        sellerOrders.add(Order1);
        sellerOrders.add(Order2);

        when(orderRepository.findAllBySellerId(sellerId)).thenReturn(sellerOrders);

        List<Order> result = service.findAllOrdersBySellerId(sellerId);

        verify(orderRepository, times(1)).findAllBySellerId(sellerId);
        assertEquals(sellerOrders, result);
    }
}