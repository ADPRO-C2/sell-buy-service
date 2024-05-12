package com.example.secondtreasurebe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private String orderId;
    private String userId;
    private List<CartListing> items;
    private double priceTotal;
    private String status;

    public Order(Cart cart) {
        UUID uuid = UUID.randomUUID();
        this.orderId = uuid.toString();
        this.userId = cart.getUserId();
        this.items = cart.getItems();
        this.priceTotal = calculatePriceTotal();
        this.status = "Dikemas";
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

    private boolean isValidStatus(String status) {
        List<String> validStatuses = Arrays.asList("Dikemas", "Di jalan", "Sudah sampai");
        return validStatuses.contains(status);
    }
}
