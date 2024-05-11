package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @InjectMocks
    CartRepository cartRepository;

    List<Cart> cartList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Cart cart1 = new Cart("c56c2def-f1d7-4462-91c2-6f2dfaf08380");

        List<CartListing> cartListings = new ArrayList<>();

        CartListing cartListing1 = new CartListing.Builder()
                .listing(new Listing())
                .amount(1)
                .build();

        CartListing cartListing2 = new CartListing.Builder()
                .listing(new Listing())
                .amount(2)
                .build();

        cartListing1.setCartListingId("e7fca96f-2fb4-493a-856c-3d770f373934");
        cartListing2.setCartListingId("e07c8f3d-9499-405d-915e-92a48474d994");
        cartListings.add(cartListing1);
        cartListings.add(cartListing2);
        cart1.setItems(cartListings);
        cartList.add(cart1);
    }

    @AfterEach
    void tearDown() {
        cartList.clear();
    }

    @Test
    void testSaveCart() {
        Cart cart = cartList.get(0);
        cartRepository.save(cart);

        Cart findSavedCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");
        assertEquals(cart.getUserId(), findSavedCart.getUserId());
        assertEquals(cart.getItems(), findSavedCart.getItems());
    }

    @Test
    void testUpdateCart() {
        Cart cart = cartList.get(0);
        cartRepository.save(cart);

        Cart updatedCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");
        List<CartListing> updatedItems = new ArrayList<>();
        CartListing newCartListing = new CartListing.Builder()
                .listing(new Listing())
                .amount(3)
                .build();

        updatedItems.add(newCartListing);
        updatedCart.setItems(updatedItems);

        cartRepository.update(updatedCart);
        Cart check = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");
        assertEquals(updatedCart.getItems(), check.getItems());
   }

    @Test
    void testDeleteCart() {
        Cart cart = cartList.get(0);
        cartRepository.save(cart);
        Cart findSavedCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");

        assertNotNull(findSavedCart);
        cartRepository.delete(cart.getUserId());
        findSavedCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");

        assertNull(findSavedCart);
    }

    @Test
    void testFindById() {
        Cart cart = cartList.get(0);
        cartRepository.save(cart);
        Cart cart2 = new Cart("d19c7d53-a48e-485e-a824-e5a1c99d6e27");
        cartRepository.save(cart2);

        Cart findCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");
        Cart check = cartList.get(0);

        assertEquals(check.getUserId(), findCart.getUserId());
        assertEquals(check.getItems(), findCart.getItems());
    }

    @Test
    void testFindAllCart() {
        Cart cart2 = new Cart("d19c7d53-a48e-485e-a824-e5a1c99d6e27");
        Cart cart3 = new Cart("50618969-f5a0-445a-b339-faea5c071b88");
        cartList.add(cart2);
        cartList.add(cart3);

        for (Cart cart : cartList) {
            cartRepository.save(cart);
        }

        List<Cart> allCarts = cartRepository.findAll();
        assertEquals(3, allCarts.size());
    }

    @Test
    void testDeleteIfNotExist() {
        Cart cart = cartList.get(0);
        cartRepository.save(cart);

        Cart findSavedCart = cartRepository.findById("c56c2def-f1d7-4462-91c2-6f2dfaf08380");

        assertNotNull(findSavedCart);

        assertThrows(NoSuchElementException.class, () -> {
            cartRepository.delete("a6ca6a62-cd90-4ff0-878d-f16da56bb5d4");
        });
    }
}