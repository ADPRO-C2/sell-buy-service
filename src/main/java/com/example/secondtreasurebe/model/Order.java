package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import static com.example.secondtreasurebe.model.OrderStatus.*;

@Getter @Setter
@NoArgsConstructor
@Table(name="orders")
@Entity
public class Order {
    //switch it to take listing name, price, amount, and maybe picture. date too, maybe.
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
    @Column(name = "cart_listing_id", nullable = false, updatable = false)
    private String cartListingid;

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

    public Order(String cartListingid) {
        this.cartListingid = cartListingid;
        this.orderId = UUID.randomUUID().toString();
        this.status = DIKEMAS;
    }

    public boolean isValidStatus(OrderStatus status) {
        List<OrderStatus> validStatuses = Arrays.asList(DIKEMAS, DI_JALAN, SUDAH_SAMPAI);
        return validStatuses.contains(status);
    }
}
