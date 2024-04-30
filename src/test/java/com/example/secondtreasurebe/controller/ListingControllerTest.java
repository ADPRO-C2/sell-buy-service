package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.service.ListingServiceImpl;
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
        Listing createdListing = new Listing();
        createdListing.setListingId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        createdListing.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        createdListing.setName("Kemeja Linen Blend");
        createdListing.setStock(10);
        createdListing.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        createdListing.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        createdListing.setPrice(299000);
        createdListing.setRateCondition(0);

        // Mock behavior dari listingService.createListing() untuk menerima ListingRequest dan mengembalikan Listing
        when(listingService.createListing(any(Listing.class))).thenReturn(createdListing);

        ObjectMapper objectMapper = new ObjectMapper();
        String listingJson = objectMapper.writeValueAsString(createdListing);

        mockMvc.perform(post("/api/sell/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(listingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.listingId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

}
