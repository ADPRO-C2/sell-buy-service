package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.service.ListingServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ListingControllerTest {
    Listing listing1;
    Listing listing2;
    private MockMvc mockMvc;

    @Mock
    private ListingServiceInterface listingService;

    @InjectMocks
    private ListingController listingRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(listingRestController).build();

        listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);

        listing2 = new Listing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing2.setListingId("eb558e9f-1c39-460e-8860-71af6af63bc8");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);
    }

    @Test
    public void testCreateListing() throws Exception {
        when(listingService.createListing(any(Listing.class))).thenReturn(listing1);

        ObjectMapper objectMapper = new ObjectMapper();
        String listingJson = objectMapper.writeValueAsString(listing1);

        mockMvc.perform(post("/api/listing/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(listingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.listingId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    public void testgetAllListings() throws Exception {
        List<Listing> listings = Arrays.asList(listing1,
                listing2);
        when(listingService.findAll()).thenReturn(listings);

        mockMvc.perform(get("/api/listings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listingId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(jsonPath("$[1].listingId").value("eb558e9f-1c39-460e-8860-71af6af63bc8"));
    }

    @Test
    public void testgetListingNotFound() throws Exception {
        when(listingService.findListingById("1")).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/listing/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testgetListingFound() throws Exception {
        when(listingService.findListingById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(listing1);
        mockMvc.perform(get("/api/listing/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listingId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(jsonPath("$.name").value("Kemeja Linen Blend"));
    }

    @Test
    public void testUpdateReview() throws Exception {
        Listing updatedListing = new Listing();
        updatedListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        updatedListing.setUserId("eb558e9f-1c39-460e-8860-12345678");
        updatedListing.setName("Compang");
        updatedListing.setPrice(30000);
        when(listingService.edit(any(Listing.class))).thenReturn(updatedListing);

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(updatedListing);

        mockMvc.perform(put("/api/listing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30000));
    }

    @Test
    public void testDeleteListingSuccess() throws Exception {
        doNothing().when(listingService).deleteListing("existingid");

        mockMvc.perform(delete("/delete-listing/existingid"))
                .andExpect(status().isOk())
                .andExpect(content().string("Listing berhasil dihapus!"));
    }

    @Test
    public void testDeleteListingNotFound() throws Exception {
        String id = "nonexistingid";

        doThrow(new NoSuchElementException()).when(listingService).deleteListing(id);

        mockMvc.perform(delete("/delete-listing/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("Listing tidak ditemukan", result.getResolvedException().getMessage()));
    }
}
