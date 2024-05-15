package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import static com.example.secondtreasurebe.model.OrderStatus.*;

@Getter @Setter
@NoArgsConstructor
@Table(name="order")
@Entity
public class Order {
    @Id
    @Size(max=100)
    @Column(name = "order_id", updatable = false, nullable = false)
    private String orderId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private int userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    private String cartListingid;

    public Order(String cartListingid) {
        this.cartListingid = cartListingid;
        this.orderId = UUID.randomUUID().toString();
        this.status = DIKEMAS;
    }

    private boolean isValidStatus(OrderStatus status) {
        List<OrderStatus> validStatuses = Arrays.asList(DIKEMAS, DI_JALAN, SUDAH_SAMPAI);
        return validStatuses.contains(status);
    }
}
