package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Checkout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutRepositoryTest {

    @InjectMocks
    CheckoutRepository checkoutRepository;

    List<Checkout> checkoutList = new ArrayList<>();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        checkoutList.clear();
    }

    @Test
    void testSaveCheckout() {
        Checkout checkout = new Checkout("bced4bac-046c-415f-9bab-2189cc1ae62c");
        checkoutRepository.save(checkout);

        Checkout savedCheckout = checkoutRepository.findById(checkout.getUserId());
        assertEquals(checkout.getUserId(), savedCheckout.getUserId());
    }

    @Test
    void testUpdateCheckout() {
        Checkout checkout = new Checkout("bced4bac-046c-415f-9bab-2189cc1ae62c");
        checkoutRepository.save(checkout);

        Checkout updatedCheckout = checkoutRepository.findById(checkout.getUserId());
        updatedCheckout.setUserId("770bf8de-f966-4c9d-8f80-16b1ae77bbe5");
        checkoutRepository.update(updatedCheckout);

        Checkout check = checkoutRepository.findById(updatedCheckout.getUserId());
        assertEquals(updatedCheckout.getUserId(), check.getUserId());
    }

    @Test
    void testDeleteCheckout() {
        Checkout checkout = new Checkout("770bf8de-f966-4c9d-8f80-16b1ae77bbe5");
        checkoutRepository.save(checkout);

        Checkout savedCheckout = checkoutRepository.findById(checkout.getUserId());
        assertNotNull(savedCheckout);

        checkoutRepository.delete(checkout.getUserId());
        assertNull(checkoutRepository.findById(checkout.getUserId()));
    }

    @Test
    void testFindCheckoutByUserId() {
        Checkout checkout1 = new Checkout("bced4bac-046c-415f-9bab-2189cc1ae62c");
        Checkout checkout2 = new Checkout("770bf8de-f966-4c9d-8f80-16b1ae77bbe5");
        checkoutRepository.save(checkout1);
        checkoutRepository.save(checkout2);

        Checkout findCheckout = checkoutRepository.findById("bced4bac-046c-415f-9bab-2189cc1ae62c");
        assertEquals("bced4bac-046c-415f-9bab-2189cc1ae62c", findCheckout.getUserId());
    }
}
