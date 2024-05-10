package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CartListingRepositoryTest {
    CartListingRepository cartListingRepository;
    List<CartListing> cartListings;

    @BeforeEach
    void setUp() {
        cartListingRepository = new CartListingRepository();
        cartListings = new ArrayList<>();

        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Nintando Swotch");
        listing1.setDescription("The best console.");
        listing1.setPrice(100000);
        listing1.setStock(45);
        listing1.setPhotoUrl("https://images.tokopedia.net/img/cache/700/VqbcmM/2023/10/7/f588f985-f66a-4749-979c-07b971cf38e9.png.webp?ect=4g");
        listing1.setRateCondition(0);

        CartListing cartListing1 = new CartListing(listing1, 4);
        cartListing1.setCartListingId("7766d08b-aa3b-4364-af55-62c282fd2b05");
        cartListingRepository.save(cartListing1);
        cartListings.add(cartListing1);
    }

    @AfterEach
    void tearDown() {
        cartListings.clear();
    }

    @Test
    void testCreateCartListing() {
        Listing listing = new Listing();
        CartListing newCartListing = new CartListing(listing, 3);
        newCartListing.setCartListingId("406f9c0d-1ac3-441d-bc25-25561a79ff5a");
        cartListingRepository.save(newCartListing);

        CartListing check = cartListingRepository.findById("406f9c0d-1ac3-441d-bc25-25561a79ff5a");

        assertEquals("406f9c0d-1ac3-441d-bc25-25561a79ff5a", check.getCartListingId());
        assertEquals(3, check.getAmount());
    }

    @Test
    void testFindCartListingById() {
        CartListing find = cartListingRepository.findById("7766d08b-aa3b-4364-af55-62c282fd2b05");

        assertNotNull(find);
        assertEquals("7766d08b-aa3b-4364-af55-62c282fd2b05", find.getCartListingId());
        assertEquals(4, find.getAmount());
    }

    @Test
    void testFindAllCartListings() {
        Listing listing2 = new Listing();
        Listing listing3 = new Listing();

        CartListing cartListing2 = new CartListing(listing2, 2);
        CartListing cartListing3 = new CartListing(listing3, 3);
        cartListing2.setCartListingId("8fa9ffb9-41ec-4ca2-a1ed-d7ef82374414");
        cartListing3.setCartListingId("627acdac-cd28-4a6a-872d-4a74e2ecb190");

        cartListingRepository.save(cartListing2);
        cartListingRepository.save(cartListing3);

        List<CartListing> allCartListings = cartListingRepository.findAll();

        assertEquals(3, allCartListings.size());

        assertEquals("8fa9ffb9-41ec-4ca2-a1ed-d7ef82374414", allCartListings.get(1).getCartListingId());
        assertEquals(2, allCartListings.get(1).getAmount());
        assertEquals("627acdac-cd28-4a6a-872d-4a74e2ecb190", allCartListings.get(2).getCartListingId());
        assertEquals(3, allCartListings.get(2).getAmount());
    }

    @Test
    void testUpdateCartListing() {
        CartListing cartListingUpdate = cartListings.get(0);

        cartListingUpdate.setAmount(5);

        cartListingRepository.update(cartListingUpdate);

        CartListing updatedCartListing = cartListingRepository.findById(cartListingUpdate.getCartListingId());

        assertEquals(5, updatedCartListing.getAmount());
    }

    @Test
    void testDeleteCartListing() {
        CartListing toDelete = cartListings.get(0);

        cartListingRepository.delete(toDelete.getCartListingId());

        assertThrows(NoSuchElementException.class, () -> {
            cartListingRepository.findById(toDelete.getCartListingId());
        });
    }

    @Test
    void testUpdateIfNotExist() {
        CartListing nonexistentCartListing = new CartListing(new Listing(), 0);
        nonexistentCartListing.setCartListingId("63b56b97-16b6-4fc1-b74d-489f432f6fa1");
        assertThrows(NoSuchElementException.class, () -> {
            cartListingRepository.update(nonexistentCartListing);
        });
    }


    @Test
    void testFindIfNotExist() {
        assertThrows(NoSuchElementException.class, () -> {
            cartListingRepository.findById("63b56b97-16b6-4fc1-b74d-489f432f6fa1");
        });
    }

    @Test
    void testDeleteIfNotExist() {
        assertThrows(NoSuchElementException.class, () -> {
            cartListingRepository.delete("63b56b97-16b6-4fc1-b74d-489f432f6fa1");
        });
    }
}