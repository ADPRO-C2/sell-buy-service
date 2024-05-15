package com.example.secondtreasurebe.model;

import com.example.secondtreasurebe.repository.ListingRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_listing")
public class CartListing {

    @NotNull
    @Column(name = "listing_id", nullable = false)
    private String listingId;

    @NotNull
    @Min(value = 1, message = "Amount has to be above zero.")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Id
    @Column(name = "cart_listing_id", updatable = false, nullable = false)
    private String cartListingId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private int userId;

    @NotNull
    @Min(value = 1, message = "Total price has to be above zero.")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public CartListing(Builder builder) {
        this.listingId = builder.listingId;
        this.amount = builder.amount;
        this.cartListingId = builder.cartListingId;
        this.userId = builder.userId;
        this.totalPrice = builder.totalPrice;
    }

    public static class Builder {
        private String listingId;
        private int amount;
        private String cartListingId;
        private int userId;
        private BigDecimal totalPrice = BigDecimal.valueOf(10.00);

        public Builder() {
            this.cartListingId = UUID.randomUUID().toString();
        }

        public Builder listingId(String listingId) {
            this.listingId = listingId;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartListing build() {
            if (listingId == null || listingId.isEmpty()) {
                throw new IllegalArgumentException("Listing ID cannot be null or empty.");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            return new CartListing(this);
        }
    }
}