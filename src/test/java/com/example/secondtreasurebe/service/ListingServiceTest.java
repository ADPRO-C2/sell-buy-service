package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.UsedListing;
import com.example.secondtreasurebe.model.ListingRequest;

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
        Listing listing1 = service.createListing(request1);

        ListingRequest request2 = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63bz9");
        request2.setListingId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        request2.setName("T-Shirt Kerah Bulat");
        request2.setStock(50);
        request2.setDescription("Enak dipakai");
        request2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        request2.setPrice(149000);
        request2.setRateCondition(2);
        Listing listing2 = service.createListing(request2);

        List<Listing> listingList = service.findAll();

        assertNotNull(listingList);
        assertFalse(listingList.isEmpty());

        System.out.println(listingList);
        System.out.println(request1);
        System.out.println(request2);

        assertTrue(listingList.contains(listing1));
        assertTrue(listingList.contains(listing2));
    }

    @Test
    void testFindProductById() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        request.setName("Kemeja Linen Blend");
        request.setStock(10);
        request.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request.setPrice(299000);
        request.setRateCondition(0);

        Listing createdListing = service.createListing(request);

        Listing foundListing = service.findListingById(createdListing.getListingId());

        assertNotNull(foundListing);
        assertEquals(createdListing.getListingId(), foundListing.getListingId());
    }

    @Test
    void testFindProductByIdNotFound() {
        ListingRequest request = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        request.setName("Kemeja Linen Blend");
        request.setStock(10);
        request.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request.setPrice(299000);
        request.setRateCondition(0);

        Listing foundListing = service.findListingById("non-existent-id");
        assertNull(foundListing);
    }

    @Test
    void testEditListing() {
        ListingRequest request1 = new ListingRequest("eb558e9f-1c39-460e-8860-71af6af63ba7");
        request1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        request1.setName("Kemeja Linen Blend");
        request1.setStock(10);
        request1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request1.setPrice(299000);
        request1.setRateCondition(0);
        Listing listing1 = service.createListing(request1);
        System.out.println(listing1.getName());

        Listing listing2 = new UsedListing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing2.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);
        System.out.println(listing2.getName());

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