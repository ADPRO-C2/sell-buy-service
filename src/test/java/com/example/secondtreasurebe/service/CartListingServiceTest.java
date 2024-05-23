package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartListingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    ListingServiceImpl listingService;

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
        Listing listing1 = new Listing();
        int amount = 2;
        listing1.setUserId(1);
        listing1.setListingId("09ea05e7-fe39-459a-9298-24a6f4099bcf");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(BigDecimal.valueOf(299000));
        listing1.setRateCondition(0);

        doReturn(listing1).when(listingService).findListingById("09ea05e7-fe39-459a-9298-24a6f4099bcf");

        CartListing.Builder builder = new CartListing.Builder();
        builder.listingId("09ea05e7-fe39-459a-9298-24a6f4099bcf")
                .amount(amount)
                .totalPrice(listing1.getPrice().multiply(BigDecimal.valueOf(amount)));

        CartListing cartListing = builder.build();
        cartListing.setUserId(1);

        when(cartListingRepository.save(any(CartListing.class))).thenReturn(cartListing);

        CartListing result = service.createCartListing("09ea05e7-fe39-459a-9298-24a6f4099bcf", amount);

        assertNotNull(result);
        assertEquals(listing1.getListingId(), result.getListingId());
        assertEquals(amount, result.getAmount());
        assertEquals(BigDecimal.valueOf(299000).multiply(BigDecimal.valueOf(amount)), result.getTotalPrice());
        assertEquals(1, result.getUserId());
    }

    @Test
    void testCreateEmptyListingId() {
        String listingId = "";
        int amount = 2;

        assertThrows(IllegalArgumentException.class, () -> service.createCartListing(listingId, amount));
    }

    @Test
    void testCreateZeroAmount() {
        String listingId = "valid-listing-id";
        int amount = 0;

        assertThrows(IllegalArgumentException.class, () -> service.createCartListing(listingId, amount));
    }

    @Test
    void testCreateListingNotFound() {
        String listingId = "listing-not-found";
        int amount = 2;
        doReturn(null).when(listingService).findListingById(listingId);

        assertThrows(NoSuchElementException.class, () -> service.createCartListing(listingId, amount));
    }

    @Test
    void testUpdateAmount() {
        CartListing cartListing = new CartListing.Builder()
                .listingId("09ea05e7-fe39-459a-9298-24a6f4099bcf")
                .amount(4)
                .userId(1)
                .build();
        cartListing.setCartListingId("7766d08b-aa3b-4364-af55-62c282fd2b05");

        int newAmount = 5;

        CartListing updated = cartListing;
        updated.setAmount(5);

        Listing listing = new Listing();
        listing.setPrice(BigDecimal.valueOf(50));

        when(cartListingRepository.findById(cartListing.getCartListingId())).thenReturn(Optional.of(cartListing));
        when(cartListingRepository.save(any(CartListing.class))).thenReturn(updated);

        when(listingService.findListingById("09ea05e7-fe39-459a-9298-24a6f4099bcf")).thenReturn(listing);

        CartListing result = service.updateAmount(cartListing.getCartListingId(), newAmount);

        verify(cartListingRepository, times(1)).save(any(CartListing.class));

        assertEquals(newAmount, result.getAmount());
        assertEquals(BigDecimal.valueOf(250), result.getTotalPrice());
    }

    @Test
    void testUpdateInvalidAmount() {
        CartListing cartListing = new CartListing.Builder()
                .listingId("notimportant")
                .amount(3)
                .userId(1)
                .totalPrice(BigDecimal.valueOf(30))
                .build();

        cartListing.setCartListingId("02bde298-d1c7-4bce-acc9-bf479a0d0154");
        int newAmount = -1;

        assertThrows(IllegalArgumentException.class, () -> service.updateAmount(cartListing.getCartListingId(), newAmount));
    }

    @Test
    void testFindById() {
        CartListing cartListing = this.cartListings.get(1);
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