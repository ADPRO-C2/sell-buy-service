package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.repository.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartServiceImpl service;

    @Mock
    CartRepository cartRepository;

    List<Cart> cartList;

    @BeforeEach
    void setUp() {
        cartList = new ArrayList<>();
        Cart cart1 = new Cart("8bd81dd7-8d6e-4bf0-84e5-45d6f7fb8234");
        cartList.add(cart1);
        Cart cart2 = new Cart("5a9f2103-ac3e-4439-95d8-21dda7db3d04");
        cartList.add(cart2);
    }

    @AfterEach
    void tearDown() {
        cartList.clear();
    }


    @Test
    void testCreateCart() {
        Cart cart = new Cart("65d35c42-b2ae-4ae6-a1d7-0315d72a0e4e");
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = service.createCart(cart);

        verify(cartRepository, times(1)).save(cart);
        assertEquals(cart, result);
    }

    @Test
    void testFindCartByUserId() {
        int userId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        Cart cart = new Cart(userId);
        when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));

        Cart result = service.findById(userId);

        verify(cartRepository, times(1)).findById(userId);
        assertEquals(cart, result);
    }

    @Test
    void testDeleteCart() {
        int userId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        Cart cart = new Cart(userId);

        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartRepository.existsById(cart.getUserId())).thenReturn(true);

        cartRepository.save(cart);

        service.deleteCart(userId);

        verify(cartRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindByIdIfNotExist() {
        String nonExistentUserId = "1a605de5-5021-42d2-a1ef-ddae7481c942";

        when(cartRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            service.findById(nonExistentUserId);
        });
    }

    @Test
    void testDeleteCartIfNotExist() {
        String nonExistentUserId = "1a605de5-5021-42d2-a1ef-ddae7481c942";

        assertThrows(NoSuchElementException.class, () -> service.deleteCart(nonExistentUserId));

        verify(cartRepository, never()).delete(any());
    }

    @Test
    void testFindAllInCart() {
        int userId = "8bd81dd7-8d6e-4bf0-84e5-45d6f7fb8234";
        Cart cart = new Cart(userId);

        CartListing cartListing1 = new CartListing.Builder()
                .listing(new Listing())
                .amount(2)
                .build();

        CartListing cartListing2 = new CartListing.Builder()
                .listing(new Listing())
                .amount(1)
                .build();

        cart.addCartListing(cartListing1);
        cart.addCartListing(cartListing2);

        when(cartRepository.findById(userId)).thenReturn(Optional.of(cart));

        List<CartListing> result = service.findAllInCart(userId);

        verify(cartRepository, times(1)).findById(userId);

        assertEquals(cart.getItems(), result);
    }

}