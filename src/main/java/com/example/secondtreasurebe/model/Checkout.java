package com.example.secondtreasurebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Getter @Setter
@NoArgsConstructor
@Table(name="checkout")
@Entity
public class Checkout {
    @Id
    @Size(max=100)
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Checkout(String userId) {
        this.userId = userId;
        this.orders = new ArrayList<>();
    }

    public void sameUser() {
        for (Order order : this.orders) {
            if (!order.getUserId().equals(this.userId)) {
                throw new IllegalArgumentException("User ID of Orders and Checkout must match.");
            }
        }
    }

    public void addToOrders(Order order) {
        if (!order.getUserId().equals(this.userId)) {
            throw new IllegalArgumentException("User ID of Order does not match Checkout.");
        }

        this.orders.add(order);
    }
}
