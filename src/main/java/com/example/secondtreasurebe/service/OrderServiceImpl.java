package com.example.secondtreasurebe.service;

import com.example.secondtreasurebe.model.CartListing;
import com.example.secondtreasurebe.model.Listing;
import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import com.example.secondtreasurebe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartListingServiceImpl service;

    @Autowired
    private ListingServiceImpl listingService;

    @Override
    public Order createOrder(String cartListingId) {
        if (cartListingId == null || cartListingId.isEmpty()) {
            throw new IllegalArgumentException("CartListing ID cannot be null or empty.");
        }

        Order order = new Order();
        String id = UUID.randomUUID().toString();

        CartListing cartListing = service.findCartListingById(cartListingId);
        String listingName = listingService.findListingById(cartListing.getListingId()).getName();
        String listingPhoto = listingService.findListingById(cartListing.getListingId()).getPhotoUrl();
        int sellerId = listingService.findListingById(cartListing.getListingId()).getUserId();

        order.setOrderId(id);
        order.setUserId(cartListing.getUserId());
        order.setAmount(cartListing.getAmount());
        order.setTotalPrice(cartListing.getTotalPrice());
        order.setListingName(listingName);
        order.setPhotoUrl(listingPhoto);
        order.setSellerId(sellerId);
        order.setDateBought(LocalDate.now());
        order.setStatus(OrderStatus.DIKEMAS);

        Order savedOrder = orderRepository.save(order);

        service.deleteCartListing(cartListingId);

        return savedOrder;
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("OrderStatus cannot be null");
        }

        try {

            String statusStr = status.toString();
            if (!Arrays.asList(OrderStatus.values()).stream().anyMatch(s -> s.toString().equals(statusStr.toUpperCase()))) {
                throw new IllegalArgumentException("Invalid OrderStatus: " + status);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid OrderStatus: " + status);
        }

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found for ID: " + orderId));

        existingOrder.setStatus(status);

        return orderRepository.save(existingOrder);
    }


    @Override
    public Order findOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found."));
    }

    @Override
    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NoSuchElementException("Order not found.");
        } else {
            orderRepository.deleteById(orderId);
        }
    }

    @Override
    public List<Order> findAllOrdersByUserId(int userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public List<Order> findAllOrdersBySellerId(int sellerId) {
        return orderRepository.findAllBySellerId(sellerId);
    }
}