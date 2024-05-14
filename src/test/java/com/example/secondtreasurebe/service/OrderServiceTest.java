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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl service;

    @Mock
    OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @Mock
    private CheckoutService checkoutService;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        Order order1 = new Order(new Cart(11));
        order1.setOrderId("a006d26e-b675-4919-bfc9-4a8936af9bba");
        orders.add(order1);
        Order order2 = new Order(new Cart(13));
        order2.setOrderId("bbd79f8c-3d06-423e-9693-02039d31401b");
        orders.add(order2);
    }

    @AfterEach
    void tearDown() {
        orders.clear();
    }

    @Test
    void testCreateOrder() {
        int userId = 14;
        Order order = new Order();
        Cart cart = new Cart(userId);
        cart.setItems(List.of(new CartListing()));
        Checkout checkout = new Checkout(userId);

        when(cartService.findById(userId)).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(checkoutService.findCheckoutById(userId)).thenReturn(checkout);

        Order createdOrder = service.createOrder(userId, order);

        assertEquals(userId, createdOrder.getUserId());
        assertFalse(createdOrder.getItems().isEmpty());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(checkoutService, times(1)).findCheckoutById(userId);
        verify(checkoutService, times(1)).createCheckout(any(Checkout.class));
        verify(cartService, times(1)).createCart(any(Cart.class));
    }

    @Test
    void testUpdateOrderStatus() {
        String orderId = "a006d26e-b675-4919-bfc9-4a8936af9bba";
        Order order = orders.get(0);
        OrderStatus newStatus = OrderStatus.DI_JALAN;

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = service.updateOrderStatus(order, newStatus);

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

        List<Order> result = service.findAllOrdersFromUser(userId);

        verify(orderRepository, times(1)).findAllByUserId(userId);
        assertEquals(userOrders.size(), result.size());
        assertTrue(result.contains(userOrder1));
    }

    @Test
    void testDeleteOrder() {
        String orderId = "a006d26e-b675-4919-bfc9-4a8936af9bba";
        Order order = new Order(new Cart(15));
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