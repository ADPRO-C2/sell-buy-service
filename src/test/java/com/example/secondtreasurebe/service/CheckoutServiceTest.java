package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Checkout;
import com.example.secondtreasurebe.repository.CheckoutRepository;
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
    void testUpdateCheckout() {
        Checkout checkout = new Checkout("0c9020f3-76f3-48ce-bfd0-1ab823a19059");
        when(checkoutRepository.update(checkout)).thenReturn(checkout);

        Checkout result = service.updateCheckout(checkout);

        verify(checkoutRepository, times(1)).update(checkout);
        assertEquals(checkout, result);
    }

    @Test
    void testFindCheckoutById() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        Checkout checkout = new Checkout(userId);
        when(checkoutRepository.findById(userId)).thenReturn(checkout);

        Checkout result = service.findCheckoutById(userId);

        verify(checkoutRepository, times(1)).findById(userId);
        assertEquals(checkout, result);
    }

    @Test
    void testDeleteCheckout() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        Checkout checkout = new Checkout(userId);
        when(checkoutRepository.findById(userId)).thenReturn(checkout);

        assertDoesNotThrow(() -> service.deleteCheckout(userId));

        verify(checkoutRepository, times(1)).delete(userId);
    }

    @Test
    void testFindByIdNotFound() {
        String userId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";
        when(checkoutRepository.findById(userId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> service.findCheckoutById(userId));

        verify(checkoutRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateNotFound() {
        Checkout checkout = new Checkout("0c9020f3-76f3-48ce-bfd0-1ab823a19059");
        when(checkoutRepository.update(checkout)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> service.updateCheckout(checkout));

        verify(checkoutRepository, times(1)).update(checkout);
    }

    @Test
    void testDeleteNotFound() {
        String nonExistentUserId = "0c9020f3-76f3-48ce-bfd0-1ab823a19059";

        when(checkoutRepository.findById(nonExistentUserId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> service.deleteCheckout(nonExistentUserId));

        verify(checkoutRepository, times(1)).findById(nonExistentUserId);
        verify(checkoutRepository, never()).delete(anyString());
    }
}
