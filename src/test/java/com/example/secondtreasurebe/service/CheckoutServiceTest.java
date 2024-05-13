package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.repository.CheckoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

    @InjectMocks
    CheckoutServiceImpl service;

    @Mock
    CheckoutRepository checkoutRepository;

    @Test
    void testCreateCheckout() {
        Checkout checkout = new Checkout("0c9020f3-76f3-48ce-bfd0-1ab823a19059");
        when(checkoutRepository.save(checkout)).thenReturn(checkout);

        Checkout result = service.createCheckout(checkout);

        verify(checkoutRepository, times(1)).save(checkout);
        assertEquals(checkout, result);
    }

    @Test
    void testFindCheckoutById() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        Checkout checkout = new Checkout(userId);
        when(checkoutRepository.findById(userId)).thenReturn(Optional.of(checkout));

        Checkout result = service.findCheckoutById(userId);

        verify(checkoutRepository, times(1)).findById(userId);
        assertEquals(checkout, result);
    }

    @Test
    void testFindAllInCheckout() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        Checkout checkout = new Checkout(userId);

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(new Cart(userId));
        Order order2 = new Order(new Cart(userId));
        orders.add(order1);
        orders.add(order2);
        checkout.setOrders(orders);

        when(checkoutRepository.findById(userId)).thenReturn(Optional.of(checkout));

        List<Order> result = service.findAllInCheckout(userId);

        verify(checkoutRepository, times(1)).findById(userId);
        assertEquals(orders.size(), result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
    }

    @Test
    void testDeleteCheckout() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        Checkout checkout = new Checkout(userId);
        when(checkoutRepository.findById(userId)).thenReturn(Optional.of(checkout));

        assertDoesNotThrow(() -> service.deleteCheckout(userId));

        verify(checkoutRepository, times(1)).delete(any());
    }

    @Test
    void testFindByIdNotFound() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        when(checkoutRepository.findById(userId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> service.findCheckoutById(userId));

        verify(checkoutRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteNotFound() {
        String nonExistentUserId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";

        when(checkoutRepository.findById(nonExistentUserId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> service.deleteCheckout(nonExistentUserId));

        verify(checkoutRepository, times(1)).findById(nonExistentUserId);
        verify(checkoutRepository, never()).delete(any());
    }
}
