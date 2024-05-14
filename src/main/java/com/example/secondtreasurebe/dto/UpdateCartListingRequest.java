package com.example.secondtreasurebe.dto;

import com.example.secondtreasurebe.model.CartListing;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCartListingRequest {
    private CartListing cartListing;
    private int amount;
}
