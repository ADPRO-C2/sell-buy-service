package com.example.secondtreasurebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinFormula;

@Getter @Setter
@NoArgsConstructor
@Table(name="cart")
@Entity
public class Cart {

    @Id
    @Size(max=100)
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartListing> items;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public void addCartListing(CartListing cartListing) {
        this.items.add(cartListing);
    }

    public void removeCartListing(CartListing cartListing) {
            this.items.remove(cartListing);
        }

    public void editCartListing(CartListing cartListing, int newAmount) {
        for (CartListing cl : this.items) {
            if (cl.getCartListingId().equals(cartListing.getCartListingId())) {
                cl.setAmount(newAmount);
                break;
            }
        }
    }
}
