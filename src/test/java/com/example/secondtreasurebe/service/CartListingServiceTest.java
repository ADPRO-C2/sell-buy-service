package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartListingRepository;
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
public class CartListingServiceTest {

    @InjectMocks
    CartListingServiceImpl service;

    @Mock
    CartListingRepository cartListingRepository;

    List<CartListing> cartListings;

    @BeforeEach
    void setUp() {
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Nintando Swotch");
        listing1.setDescription("The best console.");
        listing1.setPrice(100000);
        listing1.setStock(45);
        listing1.setPhotoUrl("https://images.tokopedia.net/img/cache/700/VqbcmM/2023/10/7/f588f985-f66a-4749-979c-07b971cf38e9.png.webp?ect=4g");
        listing1.setRateCondition(0);

        CartListing cartListing1 = new CartListing.Builder()
                .listing(listing1)
                .amount(4)
                .build();
        cartListing1.setCartListingId("7766d08b-aa3b-4364-af55-62c282fd2b05");

        Listing listing2 = new Listing();
        listing2.setUserId("b1c7c39f-1426-449e-a6fc-17db9ba7076e");
        listing2.setListingId("c74896d2-d27e-451d-8674-f756f457c6fe");
        listing2.setName("Updated Listing");
        listing2.setDescription("This is an updated listing.");
        listing2.setPrice(2000);
        listing2.setStock(20);
        listing2.setPhotoUrl("https://example.com/updated.jpg");
        listing2.setRateCondition(5);

        CartListing cartListing2 = new CartListing.Builder()
                .listing(listing2)
                .amount(2)
                .build();
        cartListing2.setCartListingId("3d71c131-e48d-4af0-9ab8-ac7444744243");

        cartListings = new ArrayList<>();
        cartListings.add(cartListing1);
        cartListings.add(cartListing2);
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
    void testUpdate() {
        CartListing cartListing = cartListings.get(0);
        int newAmount = 5;

        CartListing updatedCartListing = new CartListing.Builder()
                .listing(cartListing.getListing())
                .amount(newAmount)
                .build();
        updatedCartListing.setCartListingId(cartListing.getCartListingId());

        when(cartListingRepository.update(any(CartListing.class))).thenReturn(updatedCartListing);

        CartListing result = service.updateCartListing(updatedCartListing);

        verify(cartListingRepository, times(1)).update(updatedCartListing);

        assertEquals(newAmount, result.getAmount());
    }


    @Test
    void testFindById() {
        CartListing cartListing = cartListings.get(1);
        doReturn(cartListing).when(cartListingRepository).findById(cartListing.getCartListingId());

        CartListing result = service.findById(cartListing.getCartListingId());
        assertEquals(cartListing.getCartListingId(), result.getCartListingId());
    }

    @Test
    void testFindByIdIfNotExist() {
        doReturn(null).when(cartListingRepository).findById("38f7f57d-ce6a-4888-b361-1c31b3814168");
        assertThrows(NoSuchElementException.class, () -> {
            service.findById("38f7f57d-ce6a-4888-b361-1c31b3814168");
        });
    }

    @Test
    void testFindAllIfEmpty() {
        doReturn(new ArrayList<>()).when(cartListingRepository).findAll();

        List<CartListing> result = service.findAllCartListings();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneCartListing() {
        doReturn(cartListings).when(cartListingRepository).findAll();

        List<CartListing> result = service.findAllCartListings();

        assertEquals(cartListings.size(), result.size());
        assertEquals(cartListings.get(0).getCartListingId(), result.get(0).getCartListingId());
        assertEquals(cartListings.get(1).getCartListingId(), result.get(1).getCartListingId());
    }

    @Test
    void testDeleteCartListing() {
        CartListing cartListing = cartListings.get(0);
        doNothing().when(cartListingRepository).delete(cartListing.getCartListingId());

        service.deleteCartListing(cartListing.getCartListingId());

        verify(cartListingRepository, times(1)).delete(cartListing.getCartListingId());
    }
}