/*package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartListingRepositoryTest {
    CartListingRepository cartListingRepository;
    List<CartListing> cartListings;

    @BeforeEach
    void setUp() {
        cartListingRepository = new CartListRepository();
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
        cartListings.add(cartListing1);
    }

    @Test
    void testCreateCartListing() {
        Listing listing = new Listing();

        CartListing newCartListing = new CartListing(listing, 3);
        cartListingRepository.save(newCartListing);

        assertTrue(cartListings.contains(newCartListing));
    }

    @Test
    void testFindCartListingById() {
        CartListing find = cartListingRepository.findById("1");

        assertNotNull(find);
        assertEquals("1", find.getListingId());
    }

    @Test
    void testFindAllCartListings() {

    }

    @Test
    void testUpdateCartListing() {
        // Retrieve an existing cart listing
        CartListing cartListingToUpdate = cartListings.get(0);

        // Update its properties
        cartListingToUpdate.setAmount(5);

        // Save the updated cart listing to the repository
        cartListingRepository.save(cartListingToUpdate);

        // Retrieve the updated cart listing from the repository
        CartListing updatedCartListing = cartListingRepository.findById(cartListingToUpdate.getId());

        // Verify that the updated cart listing has the new amount
        assertEquals(5, updatedCartListing.getAmount());
    }

    @Test
    void testDeleteCartListing() {
        // Retrieve an existing cart listing
        CartListing cartListingToDelete = cartListings.get(0);

        // Delete the cart listing from the repository
        cartListingRepository.delete(cartListingToDelete.getId());

        // Verify that the cart listing is no longer in the repository
        assertNull(cartListingRepository.findById(cartListingToDelete.getId()));
    }

    @Test
    void testUpdateIfNotExist() {

    }

    @Test
    void findIfNotExist() {

    }

    @Test
    void deleteIfNotExist() {

    }
}*/