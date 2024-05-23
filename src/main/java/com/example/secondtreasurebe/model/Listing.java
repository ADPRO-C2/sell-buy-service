package com.example.secondtreasurebe.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@Table(name="listing")
@Entity
public class Listing {
    @Id
    @Column(name = "listingid", updatable = false, nullable = false)
    private String listingId;

    @NotNull
    @Column(name="userid", nullable=false)
    private int userId;

    @NotNull
    @Column(name="name", nullable=false)
    private String name;

    @NotNull
    @Column(name="description", nullable=false)
    private String description;

    @NotNull
    @Min(value = 0, message = "Price must be greater than or equal to zero")
    @Column(name="price", nullable=false)
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Stock must be greater than or equal to zero")
    @Column(name="stock", nullable=false)
    private int stock;

    @NotNull
    @Column(name="photo_url", nullable=false)
    private String photoUrl;

    @NotNull
    @Min(value = 0, message = "Rate condition harus berada di antara 0,1,2,3")
    @Max(value = 3, message = "Rate condition harus berada di antara 0,1,2,3")
    @Column(name="rate_condition", nullable=false)
    private int rateCondition; //0: baru; 1:masih bagus; 2:rusak ringan; 3:rusak sedang

    public void validate() {
        if (price.compareTo(BigDecimal.ZERO) < 0 || stock < 0) {
            throw new IllegalArgumentException("Price or stock must be non-negative");
        } else if (rateCondition > 3 || rateCondition < 0) {
            throw new IllegalArgumentException("Rate condition must be between 0 and 3");
        }
    }
}