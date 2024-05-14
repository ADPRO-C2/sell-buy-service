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
        Cart cart1 = new Cart(11);
        cartList.add(cart1);
        Cart cart2 = new Cart(12);
        cartList.add(cart2);
    }

    @AfterEach
    void tearDown() {
        cartList.clear();
    }


    @Test
    void testCreateCart() {
        Cart cart = new Cart(13);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart result = service.createCart(cart);

        verify(cartRepository, times(1)).save(cart);
        assertEquals(cart, result);
    }

    @Test
    void testFindCartByUserId() {
        int userId = 14;
        Cart cart = new Cart(userId);
        when(cartRepository.findById(String.valueOf(userId))).thenReturn(Optional.of(cart));

        Cart result = service.findById(userId);

        verify(cartRepository, times(1)).findById(String.valueOf(userId));
        assertEquals(cart, result);
    }

    @Test
    void testDeleteCart() {
        int userId = 14;
        Cart cart = new Cart(userId);

        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartRepository.existsById(String.valueOf((cart.getUserId())))).thenReturn(true);

        cartRepository.save(cart);

        service.deleteCart(userId);

        verify(cartRepository, times(1)).deleteById(String.valueOf(userId));
    }

    @Test
    void testFindByIdIfNotExist() {
        int nonExistentUserId = 14;

        when(cartRepository.findById(String.valueOf(nonExistentUserId))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            service.findById(nonExistentUserId);
        });
    }

    @Test
    void testDeleteCartIfNotExist() {
        int nonExistentUserId = 14;

        assertThrows(NoSuchElementException.class, () -> service.deleteCart(nonExistentUserId));

        verify(cartRepository, never()).delete(any());
    }

    @Test
    void testFindAllInCart() {
        int userId = 11;
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

        when(cartRepository.findById(String.valueOf(userId))).thenReturn(Optional.of(cart));

        List<CartListing> result = service.findAllInCart(userId);

        verify(cartRepository, times(1)).findById(String.valueOf(userId));

        assertEquals(cart.getItems(), result);
    }

}