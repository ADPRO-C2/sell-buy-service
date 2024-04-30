package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class ListingServiceTest {
    @InjectMocks
    private ListingServiceInterface service;

    private Listing listing1, listing2;

    @BeforeEach
    void setUp() {
        listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);

        listing2 = new Listing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing2.setListingId(listing1.getListingId());
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);

        service = new ListingServiceImpl();

    }

    @Test
    public void testCreateListing_Success() {
        Listing createdListing = service.createListing(listing1);

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", createdListing.getUserId());
        assertNotNull(createdListing);
        assertNotNull(createdListing.getListingId());
        assertEquals("Kemeja Linen Blend", createdListing.getName());
        assertEquals(10, createdListing.getStock());
        assertEquals("Kerah terbuka, bahan nyaman dipakai.", createdListing.getDescription());
        assertEquals("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750", createdListing.getPhotoUrl());
        assertEquals(299000, createdListing.getPrice());
        assertEquals(0, createdListing.getRateCondition());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Listing> listingList = service.findAll();
        assertNotNull(listingList);
        assertTrue(listingList.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneListing() {
        service.createListing(listing1);
        service.createListing(listing2);

        List<Listing> listingList = service.findAll();

        assertNotNull(listingList);
        assertFalse(listingList.isEmpty());

        assertTrue(listingList.contains(listing1));
        assertTrue(listingList.contains(listing2));
    }

    @Test
    void testFindProductById() {
        Listing createdListing = service.createListing(listing1);

        Listing foundListing = service.findListingById(createdListing.getListingId());

        assertNotNull(foundListing);
        assertEquals(createdListing.getListingId(), foundListing.getListingId());

        foundListing = service.findListingById("non-existent-id");
        assertNull(foundListing);
    }

    @Test
    void testEditListing() {
        service.createListing(listing1);

        listing2.setListingId(listing1.getListingId());

        service.edit(listing2);

        Listing edited = service.findListingById(listing1.getListingId());

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
}