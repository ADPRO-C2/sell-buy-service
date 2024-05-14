package com.example.secondtreasurebe.dto;

import com.example.secondtreasurebe.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrderRequest {
    private String userId;
    private Order order;
}
