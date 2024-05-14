package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    OrderRepository orderRepository;

    @Test
    void testFindAllByUserId() {
        int userId = "98726832-2f2e-45d4-bd67-6def918fdaa6";
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder("77fcb080-a499-4e50-a610-c5fe49841f1b", userId));
        orders.add(createOrder("5c0803ec-54aa-405c-8f46-460176365cf3", userId));
        when(orderRepository.findAllByUserId(userId)).thenReturn(orders);

        List<Order> foundOrders = orderRepository.findAllByUserId(userId);

        assertEquals(2, foundOrders.size());
        assertEquals(userId, foundOrders.get(0).getUserId());
        assertEquals(userId, foundOrders.get(1).getUserId());
    }

    @Test
    void testFindAllByUserIdNotFound() {
        int userId = "0bfe6cb8-b193-4a83-bbee-83ef3e3e65fe";
        when(orderRepository.findAllByUserId(userId)).thenReturn(new ArrayList<>());

        List<Order> foundOrders = orderRepository.findAllByUserId(userId);

        assertEquals(0, foundOrders.size());
    }

    private Order createOrder(String orderId, int userId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setPriceTotal(100);
        order.setStatus(OrderStatus.DIKEMAS);
        return order;
    }
}
