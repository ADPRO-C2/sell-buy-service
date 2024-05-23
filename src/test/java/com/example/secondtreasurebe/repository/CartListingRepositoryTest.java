package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartListingRepositoryTest {

    @Mock
    CartListingRepository cartListingRepository;

    @Test
    void testFindAllByUserId() {
        int userId = 1;
        List<CartListing> cartListings = new ArrayList<>();
        cartListings.add(new CartListing.Builder()
                .listingId("listing1")
                .amount(2)
                .userId(userId)
                .build());
        cartListings.add(new CartListing.Builder()
                .listingId("listing2")
                .amount(3)
                .userId(userId)
                .build());

        when(cartListingRepository.findAllByUserId(userId)).thenReturn(cartListings);
        List<CartListing> result = cartListingRepository.findAllByUserId(userId);

        assertEquals(cartListings.size(), result.size());
    }

    @Test
    void testFindAllByUserIdNotFound() {
        int userId = 1;

        when(cartListingRepository.findAllByUserId(userId)).thenReturn(new ArrayList<>());
        List<CartListing> result = cartListingRepository.findAllByUserId(userId);

        assertEquals(0, result.size());
    }
}
