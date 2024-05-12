package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.Cart;
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
    void testUpdateCart() {
        Cart cart = new Cart("1a605de5-5021-42d2-a1ef-ddae7481c942");
        when(cartRepository.update(cart)).thenReturn(cart);

        Cart result = service.updateCart(cart);

        verify(cartRepository, times(1)).update(cart);
        assertEquals(cart, result);
    }

    @Test
    void testFindCartByUserId() {
        String userId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        Cart cart = new Cart(userId);
        when(cartRepository.findById(userId)).thenReturn(cart);

        Cart result = service.findById(userId);

        verify(cartRepository, times(1)).findById(userId);
        assertEquals(cart, result);
    }

    @Test
    void testFindAllCart() {
        when(cartRepository.findAll()).thenReturn(cartList);

        List<Cart> result = service.findAllCarts();

        verify(cartRepository, times(1)).findAll();
        assertEquals(cartList, result);
    }

    @Test
    void testDeleteCart() {
        String userId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        doNothing().when(cartRepository).delete(userId);

        service.deleteCart(userId);

        verify(cartRepository, times(1)).delete(userId);
    }

    @Test
    void testFindByIdIfNotExist() {
        String nonExistentUserId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        when(cartRepository.findById(nonExistentUserId)).thenReturn(null);

        Cart result = service.findById(nonExistentUserId);

        verify(cartRepository, times(1)).findById(nonExistentUserId);
        assertNull(result);
    }

    @Test
    void testDeleteCartIfNotExist() {
        String nonExistentUserId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        doThrow(NoSuchElementException.class).when(cartRepository).delete(nonExistentUserId);

        assertThrows(NoSuchElementException.class, () -> service.deleteCart(nonExistentUserId));

        verify(cartRepository, times(1)).delete(nonExistentUserId);
    }

    @Test
    void testUpdateCartIfNotExist() {
        String nonExistentUserId = "1a605de5-5021-42d2-a1ef-ddae7481c942";
        Cart nonExistentCart = new Cart(nonExistentUserId);
        doThrow(NoSuchElementException.class).when(cartRepository).update(nonExistentCart);

        assertThrows(NoSuchElementException.class, () -> service.updateCart(nonExistentCart));

        verify(cartRepository, times(1)).update(nonExistentCart);
    }

}
