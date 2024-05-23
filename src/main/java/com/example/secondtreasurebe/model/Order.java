package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import static com.example.secondtreasurebe.model.OrderStatus.*;

@Getter @Setter
@NoArgsConstructor
@Table(name="orders")
@Entity
public class Order {
    @Id
    @Size(max=100)
    @Column(name = "order_id", updatable = false, nullable = false)
    private String orderId;

    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private int userId;

    @NotNull
    @Column(name = "seller_id", nullable = false, updatable = false)
    private int sellerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "listing_name", updatable = false, nullable = false)
    private String listingName;

    @NotNull
    @Column(name = "amount", updatable = false, nullable = false)
    private int amount;

    @NotNull
    @Column(name = "total_price", updatable = false, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "listing_photo", updatable = false)
    private String photoUrl;

    @NotNull
    @Column(name = "date_bought", updatable = false, nullable = false)
    private LocalDate dateBought;

    public Order(String cartListingId) {

    }
    public boolean isValidStatus(OrderStatus status) {
        if (status == null || !Arrays.asList(DIKEMAS, DI_JALAN, SUDAH_SAMPAI).contains(status)) {
            throw new IllegalArgumentException("Invalid Order Status: " + status);
        }
        return true;
    }
}