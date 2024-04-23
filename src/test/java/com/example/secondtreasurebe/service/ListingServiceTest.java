package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.ListingRequest;
import com.example.secondtreasurebe.service

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

    @BeforeEach
    void setUp() {
        service = new ListingServiceImpl();
    }

    @Test
    public void testCreateListing_Success() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        request.setName("Kemeja Linen Blend");
        request.setStock(10);
        request.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request.setPrice(299000);
        request.setRateCondition(0);

        Listing createdListing = service.createListing(request);

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63ba7", createdListing.getUserId());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", createdListing.getListingId());
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
        ListingRequest request1 = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        request1.setName("Kemeja Linen Blend");
        request1.setStock(10);
        request1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request1.setPrice(299000);
        request1.setRateCondition(0);
        service.createListing(request1);

        ListingRequest request2 = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63bz9");
        request2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        request2.setName("T-Shirt Kerah Bulat");
        request2.setStock(50);
        request2.setDescription("Enak dipakai");
        request2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        request2.setPrice(149000);
        request2.setRateCondition(2);
        service.createListing(request2);

        List<Listing> listingList = service.findAll();

        assertNotNull(listingList);
        assertFalse(listingList.isEmpty());

        System.out.println(listingList);

        assertTrue(listingList.contains(request1));
        assertTrue(listingList.contains(request2));
    }
}