package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.service.CartListingServiceImpl;
import com.example.secondtreasurebe.service.ListingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartListingControllerTest {

    @InjectMocks
    private CartListingController controller;

    @Mock
    private CartListingServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCartListingSuccess() {
        String listingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        int amount = 2;

        CartListing expectedCartListing = new CartListing();
        expectedCartListing.setListingId(listingId);
        expectedCartListing.setAmount(amount);

        Mockito.when(service.createCartListing(listingId, amount, 1)).thenReturn(expectedCartListing);

        ResponseEntity<CartListing> response = controller.createCartListing(listingId, amount, 1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedCartListing, response.getBody());
    }

    @Test
    public void testCreateCartListing_ListingNotFound() {
        String listingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        int amount = 2;

        Mockito.when(service.createCartListing(listingId, amount, 1)).thenThrow(new NoSuchElementException("Listing not found"));

        try {
            controller.createCartListing(listingId, amount, 1);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("Listing not found"));
        }
    }

    @Test
    public void testUpdateAmountSuccess() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        int newAmount = 3;

        CartListing expectedCartListing = new CartListing();
        expectedCartListing.setCartListingId(cartListingId);
        expectedCartListing.setAmount(newAmount);

        Mockito.when(service.updateAmount(cartListingId, newAmount)).thenReturn(expectedCartListing);

        ResponseEntity<CartListing> response = controller.updateAmount(cartListingId, newAmount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCartListing, response.getBody());
    }

    @Test
    public void testUpdateAmount_CartListingNotFound() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";
        int newAmount = 3;

        Mockito.when(service.updateAmount(cartListingId, newAmount)).thenThrow(new NoSuchElementException("CartListing not found"));

        try {
            controller.updateAmount(cartListingId, newAmount);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("CartListing not found"));
        }
    }

    @Test
    public void testGetCartListingByIdSuccess() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        CartListing expectedCartListing = new CartListing();
        expectedCartListing.setCartListingId(cartListingId);

        Mockito.when(service.findCartListingById(cartListingId)).thenReturn(expectedCartListing);

        ResponseEntity<CartListing> response = controller.getCartListingById(cartListingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCartListing, response.getBody());
    }

    @Test
    public void testGetAllCartListingsByUserIdSuccess() {
        int userId = 1;
        List<CartListing> expectedCartListings = Arrays.asList(new CartListing(), new CartListing());

        Mockito.when(service.findAllCartListingsByUserId(userId)).thenReturn(expectedCartListings);

        ResponseEntity<List<CartListing>> response = controller.getAllCartListingsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCartListings, response.getBody());
    }

    @Test
    public void testGetAllCartListingsByUserId_UserNotFound() {
        int userId = 10;

        Mockito.when(service.findAllCartListingsByUserId(userId)).thenThrow(new NoSuchElementException("User not found"));

        try {
            controller.getAllCartListingsByUserId(userId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("User not found"));
        }
    }

    @Test
    public void testDeleteCartListingSuccess() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.doNothing().when(service).deleteCartListing(cartListingId);

        ResponseEntity<String> response = controller.deleteCartListing(cartListingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CartListing deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteCartListing_CartListingNotFound() {
        String cartListingId = "0f952a49-324a-436c-bb24-5b0ce9fc9981";

        Mockito.doThrow(new NoSuchElementException("CartListing not found")).when(service).deleteCartListing(cartListingId);

        try {
            controller.deleteCartListing(cartListingId);
            fail("Expected ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertTrue(e.getMessage().contains("CartListing not found"));
        }
    }
}
