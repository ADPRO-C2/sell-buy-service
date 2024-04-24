package com.example.secondtreasurebe.controller;

import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.ListingRequest;
import com.example.secondtreasurebe.model.NewListing;
import com.example.secondtreasurebe.service.ListingServiceImpl;
import com.example.secondtreasurebe.service.ListingServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ListingControllerTest {

    @Mock
    private ListingServiceInterface listingService;

    private ListingRequest request;
    @InjectMocks
    private ListingController listingRestController;

    private MockMvc mockMvc;

    public ListingControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(listingRestController).build();
    }
    @BeforeEach
    public void setup() {
        listingService = new ListingServiceImpl();
        listingRestController = new ListingController();

        request = new ListingRequest();
        request.setName("Kemeja Linen Blend");
        request.setStock(10);
        request.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request.setPrice(299000);
        request.setRateCondition(0);
    }

    @Test
    public void testCreateListing() throws Exception {
        ListingRequest request = new ListingRequest();
        request.setName("Kemeja Linen Blend");
        request.setStock(10);
        request.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        request.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        request.setPrice(299000);
        request.setRateCondition(0);

        Listing createdListing = new NewListing();
        createdListing.setName("Kemeja Linen Blend");
        createdListing.setStock(10);
        createdListing.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        createdListing.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        createdListing.setPrice(299000);
        createdListing.setRateCondition(0);

        // Mock behavior dari listingService.createListing() untuk menerima ListingRequest dan mengembalikan Listing
        when(listingService.createListing(any(ListingRequest.class))).thenReturn(createdListing);

        // Act
        ResponseEntity<Listing> responseEntity = listingRestController.createListing(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdListing, responseEntity.getBody());

        verify(listingService).createListing(request);
    }

}
