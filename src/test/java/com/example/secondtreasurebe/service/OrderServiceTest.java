package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.*;
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
    ListingServiceImpl listingService;

    @Mock
    CartListingServiceImpl cartListingService;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        Order order1 = new Order("cartlistingId-1");
        order1.setOrderId("a006d26e-b675-4919-bfc9-4a8936af9bba");
        order1.setUserId(11);
        orders.add(order1);
        Order order2 = new Order("cartlistingId-2");
        order2.setOrderId("bbd79f8c-3d06-423e-9693-02039d31401b");
        order2.setUserId(12);
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

        builder = new CartListing.Builder()
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

        when(listingService.findListingById(listing1.getListingId())).thenReturn(listing1);
        when(cartListingService.findCartListingById(cartListing.getCartListingId())).thenReturn(cartListing);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.createOrder("12");

        assertEquals(cartListing.getUserId(), result.getUserId());
        assertEquals(OrderStatus.DIKEMAS, result.getStatus());
        assertEquals(cartListing.getAmount(), result.getAmount());
        assertEquals(cartListing.getTotalPrice(), result.getTotalPrice());
        assertEquals(listing1.getName(), result.getListingName());
        assertEquals(listing1.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(listing1.getUserId(), result.getSellerId());
        assertEquals(LocalDate.now(), result.getDateBought());
        assertEquals(OrderStatus.DIKEMAS, result.getStatus());
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
}