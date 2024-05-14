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
    @Size(max=100)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartListing> items;

    @ManyToOne
    @JoinColumn(name = "checkout_id", referencedColumnName = "user_id")
    private Checkout checkout;

    @NotNull
    @Min(value = 0, message = "Price total has to be zero or above.")
    @Column(name = "price_total", nullable = false)
    private double priceTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    public Order(Cart cart) {
        UUID uuid = UUID.randomUUID();
        this.orderId = uuid.toString();
        this.userId = cart.getUserId();
        this.items = cart.getItems();
        this.priceTotal = calculatePriceTotal();
        this.status = DIKEMAS;
    }

    private double calculatePriceTotal() {
        double total = 0;
        for (CartListing item : items) {
            total += item.getAmount() * item.getListing().getPrice();
        }
        return total;
    }

    public void validateOrder() {
        if (this.priceTotal < 0) {
            throw new IllegalArgumentException("Price total should be more than 0.");
        } else if (!isValidStatus(this.status)) {
            throw new IllegalArgumentException("Invalid order status.");
        }
    }

    private boolean isValidStatus(OrderStatus status) {
        List<OrderStatus> validStatuses = Arrays.asList(DIKEMAS, DI_JALAN, SUDAH_SAMPAI);
        return validStatuses.contains(status);
    }
}
