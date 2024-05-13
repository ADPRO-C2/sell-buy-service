package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.ListingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {
    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImpl service;

    private Listing listing1, listing2;

    @BeforeEach
    void setUp() {
        listing1 = new Listing();
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);

        listing2 = new Listing();
        listing2.setListingId(listing1.getListingId());
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);
    }

    @Test
    public void testCreateListing_Success() {
        when(listingRepository.save(listing1)).thenReturn(listing1);
        Listing createdListing = service.createListing(listing1);

        assertEquals(1, createdListing.getUserId());
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
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing1, listing2));
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
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing1, listing2));
        Listing createdListing = service.createListing(listing1);

        Listing foundListing = service.findListingById(createdListing.getListingId());

        assertNotNull(foundListing);
        assertEquals(createdListing.getListingId(), foundListing.getListingId());

        foundListing = service.findListingById("non-existent-id");
        assertNull(foundListing);
    }

    @Test
    void testEditListing() {
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing1));
        when(listingRepository.save(ArgumentMatchers.any(Listing.class))).thenReturn(listing1);
        service.createListing(listing1);

        listing1.setPrice(12000);
        listing1.setName("Baju Compang Camping");

        Listing edited = service.edit(listing1);

        // Verifikasi bahwa produk telah diubah dengan benar
        assertEquals("Baju Compang Camping", edited.getName());
        assertEquals(12000, edited.getPrice());

        // Verifikasi bahwa data produk yang disimpan telah diperbarui
        assertEquals(edited.getName(), listing1.getName());
        assertEquals(edited.getPrice(), listing1.getPrice());
    }

    @Test
    public void testEditListingNotFound() {
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing1));
        assertThrows(ResponseStatusException.class, () -> {
            service.edit(listing2);
        });
    }

    @Test
    public void testDeleteListingSuccess() {
        String id = "existing-id";

        when(listingRepository.existsById(id)).thenReturn(true);
        doNothing().when(listingRepository).deleteById(id);

        assertDoesNotThrow(() -> service.deleteListing(id));

        verify(listingRepository, times(1)).existsById(id);
        verify(listingRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteListingNotFound() {
        String id = "non-existing-id";

        when(listingRepository.existsById(id)).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> service.deleteListing(id));

        assertEquals("No listing found with ID: " + id, exception.getMessage());

        verify(listingRepository, times(1)).existsById(id);
        verify(listingRepository, never()).deleteById(id);
    }
}