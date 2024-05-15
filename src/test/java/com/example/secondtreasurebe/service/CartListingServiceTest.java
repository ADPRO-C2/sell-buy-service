package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartListingRepository;
import com.example.secondtreasurebe.repository.ListingRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartListingServiceTest {

    @InjectMocks
    CartListingServiceImpl service;

    @Mock
    CartListingRepository cartListingRepository;

    @Mock
    ListingRepository listingRepository;

    List<CartListing> cartListings;

    @BeforeEach
    void setUp() {
        CartListing cartListing1 = new CartListing.Builder()
                .listingId("09ea05e7-fe39-459a-9298-24a6f4099bcf")
                .amount(4)
                .userId(1)
                .build();
        cartListing1.setCartListingId("7766d08b-aa3b-4364-af55-62c282fd2b05");

        CartListing cartListing2 = new CartListing.Builder()
                .listingId("77d9a3ef-6be7-4046-b940-8b1a24a24a6a")
                .amount(2)
                .userId(2)
                .build();
        cartListing2.setCartListingId("3d71c131-e48d-4af0-9ab8-ac7444744243");

        cartListings = new ArrayList<>();
        cartListings.add(cartListing1);
        cartListings.add(cartListing2);
    }

    @AfterEach
    void tearDown() {
        cartListings.clear();
    }

    @Test
    void testCreate() {
        CartListing cartListing = cartListings.get(0);
        doReturn(cartListing).when(cartListingRepository).save(cartListing);

        CartListing result = service.createCartListing(cartListing);
        verify(cartListingRepository, times(1)).save(cartListing);
        assertEquals(cartListing.getCartListingId(), result.getCartListingId());
    }

    @Test
    void testUpdateAmount() {
        CartListing cartListing = cartListings.get(0);
        int newAmount = 5;

        CartListing updatedCartListing = new CartListing.Builder()
                .listingId(cartListing.getListingId())
                .amount(newAmount)
                .build();
        updatedCartListing.setCartListingId(cartListing.getCartListingId());

        when(cartListingRepository.save(any(CartListing.class))).thenReturn(updatedCartListing);

        CartListing result = service.updateAmount(updatedCartListing, newAmount);

        verify(cartListingRepository, times(1)).save(updatedCartListing);

        assertEquals(newAmount, result.getAmount());
    }


    @Test
    void testFindById() {
        CartListing cartListing = cartListings.get(1);
        when(cartListingRepository.findById(cartListing.getCartListingId())).thenReturn(Optional.of(cartListing));

        CartListing result = service.findCartListingById(cartListing.getCartListingId());
        assertEquals(cartListing.getCartListingId(), result.getCartListingId());
    }


    @Test
    void testFindByIdIfNotExist() {
        when(cartListingRepository.findById("38f7f57d-ce6a-4888-b361-1c31b3814168")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            service.findCartListingById("38f7f57d-ce6a-4888-b361-1c31b3814168");
        });
    }

    @Test
    void testDeleteCartListing() {
        CartListing cartListing = cartListings.get(0);

        when(cartListingRepository.save(cartListing)).thenReturn(cartListing);
        cartListingRepository.save(cartListing);

        when(cartListingRepository.existsById(cartListing.getCartListingId())).thenReturn(true);

        service.deleteCartListing(cartListing.getCartListingId());

        verify(cartListingRepository, times(1)).deleteById(cartListing.getCartListingId());
    }

    @Test
    void testDeleteIfCartListingNotFound() {
        String cartListingId = "cartlistingid";

        assertThrows(NoSuchElementException.class, () -> service.deleteCartListing(cartListingId));

        verify(cartListingRepository, never()).delete(any());
    }

    @Test
    void testFindAllByUserId() {
        List<CartListing> mock = new ArrayList<>();
        mock.add(cartListings.get(0));

        when(cartListingRepository.findAllByUserId(1)).thenReturn(mock);

        List<CartListing> findList = service.findAllCartListingsByUserId(1);

        assertEquals(mock, findList);
    }
}