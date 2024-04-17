package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.NewListing;
import com.example.secondtreasurebe.model.UsedListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListingRepositoryTest {
    @InjectMocks
    ListingRepository listingRepository;

    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Listing listing1 = new NewListing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.create(listing1);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedProduct = listingIterator.next();
        assertEquals(listing1.getListingId(), savedProduct.getListingId());
        assertEquals(listing1.getName(), savedProduct.getName());
        assertEquals(listing1.getUserId(), savedProduct.getUserId());
        assertEquals(listing1.getStock(), savedProduct.getStock());
        assertEquals(listing1.getDescription(), savedProduct.getDescription());
        assertEquals(listing1.getPhotoUrl(), savedProduct.getPhotoUrl());
        assertEquals(listing1.getPrice(), savedProduct.getPrice());
        assertEquals(listing1.getRateCondition(), savedProduct.getRateCondition());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
        Listing listing1 = new NewListing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.create(listing1);

        Listing listing2 = new UsedListing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63bz9");
        listing2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);
        listingRepository.create(listing2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedProduct = listingIterator.next();
        assertEquals(listing1.getListingId(), savedProduct.getListingId());
        savedProduct = listingIterator.next();
        assertEquals(listing2.getListingId(), savedProduct.getListingId());
        assertFalse(listingIterator.hasNext());
    }
}