package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListing {
    private Listing listing;
    private int amount;

    public CartListing(Listing listing, int amount) {
        this.listing = listing;
        this.amount = amount;
    }

    public String getUserId() {
        return this.listing.getUserId();
    }

    public String getListingId() {
        return this.listing.getListingId();
    }

    public String getName() {
        return this.listing.getName();
    }

    public String getDescription() {
        return this.listing.getDescription();
    }

    public int getPrice() {
        return this.listing.getPrice();
    }

    public int getStock() {
        return this.listing.getStock();
    }

    public String getPhotoUrl() {
        return this.listing.getPhotoUrl();
    }

    public int getRateCondition() {
        return this.listing.getRateCondition();
    }

    public void setUserId(String userId) {
        this.listing.setUserId(userId);
    }

    public void setListingId(String listingId) {
        this.listing.setListingId(listingId);
    }

    public void setName(String name) {
        this.listing.setName(name);
    }

    public void setDescription(String description) {
        this.listing.setDescription(description);
    }

    public void setPrice(int price) {
        this.listing.setPrice(price);
    }

    public void setStock(int stock) {
        this.listing.setStock(stock);
    }

    public void setPhotoUrl(String photoUrl) {
        this.listing.setPhotoUrl(photoUrl);
    }

    public void setRateCondition(int rateCondition) {
        this.listing.setRateCondition(rateCondition);
    }

    public void validateAmount() {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount of bought listings has to be over 0.");
        }
    }
}
