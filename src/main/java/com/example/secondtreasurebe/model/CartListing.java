package com.example.secondtreasurebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "cartListing")
@Entity
public class CartListing {

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "userId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "listingid")
    private Listing listing;

    @NotNull
    @Min(value = 0, message = "Amount has to be zero or above.")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Id
    @Size(max = 100)
    @Column(name = "cartListingId", updatable = false, nullable = false)
    private String cartListingId;

    public CartListing(Builder builder) {
        this.listing = builder.listing;
        this.amount = builder.amount;
        this.cartListingId = builder.cartListingId;
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

    public static class Builder {
        private Listing listing;
        private int amount;
        private String cartListingId;

        public Builder() {
            this.cartListingId = UUID.randomUUID().toString();
        }

        public Builder listing(Listing listing) {
            this.listing = listing;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public CartListing build() {
            if (listing == null) {
                throw new IllegalArgumentException("Listing cannot be null.");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0.");
            }
            return new CartListing(this);
        }
    }
}
