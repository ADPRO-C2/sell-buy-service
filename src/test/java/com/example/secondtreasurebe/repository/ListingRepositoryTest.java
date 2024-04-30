package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
class ListingRepositoryTest {
    @InjectMocks
    ListingRepository listingRepository;

    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.createListing(listing1);

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
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.createListing(listing1);

        Listing listing2 = new Listing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63bz9");
        listing2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);
        listingRepository.createListing(listing2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        assertTrue(listingIterator.hasNext());
        Listing savedProduct = listingIterator.next();
        assertEquals(listing1.getListingId(), savedProduct.getListingId());
        savedProduct = listingIterator.next();
        assertEquals(listing2.getListingId(), savedProduct.getListingId());
        assertFalse(listingIterator.hasNext());
    }

    @Test
    void testEditListing() {
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.createListing(listing1);

        Listing listing2 = new Listing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing2.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        listingRepository.edit(listing2);

        Listing edited = listingIterator.next();

        // Verifikasi bahwa produk telah diubah dengan benar
        assertEquals(listing2.getListingId(), edited.getListingId());
        assertEquals(listing2.getName(), edited.getName());
        assertEquals(listing2.getUserId(), edited.getUserId());
        assertEquals(listing2.getStock(), edited.getStock());
        assertEquals(listing2.getDescription(), edited.getDescription());
        assertEquals(listing2.getPhotoUrl(), edited.getPhotoUrl());
        assertEquals(listing2.getPrice(), edited.getPrice());
        assertEquals(listing2.getRateCondition(), edited.getRateCondition());

        // Verifikasi bahwa data produk yang disimpan telah diperbarui
        assertEquals(listing2.getListingId(), listing1.getListingId());
        assertEquals(listing2.getName(), listing1.getName());
        assertEquals(listing2.getUserId(), listing1.getUserId());
        assertEquals(listing2.getStock(), listing1.getStock());
        assertEquals(listing2.getDescription(), listing1.getDescription());
        assertEquals(listing2.getPhotoUrl(), listing1.getPhotoUrl());
        assertEquals(listing2.getPrice(), listing1.getPrice());
        assertEquals(listing2.getRateCondition(), listing1.getRateCondition());
    }

    @Test
    void testEditListingNotFound() {
        Listing listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);
        listingRepository.createListing(listing1);

        Listing listing2 = new Listing();
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);

        Iterator<Listing> listingIterator = listingRepository.findAll();
        listingRepository.edit(listing2);

        Listing edited = listingIterator.next();

        // Verifikasi bahwa produk telah diubah dengan benar
        assertNotEquals(listing2.getListingId(), edited.getListingId());
        assertNotEquals(listing2.getName(), edited.getName());
        assertNotEquals(listing2.getUserId(), edited.getUserId());
        assertNotEquals(listing2.getStock(), edited.getStock());
        assertNotEquals(listing2.getDescription(), edited.getDescription());
        assertNotEquals(listing2.getPhotoUrl(), edited.getPhotoUrl());
        assertNotEquals(listing2.getPrice(), edited.getPrice());
        assertNotEquals(listing2.getRateCondition(), edited.getRateCondition());
    }
}