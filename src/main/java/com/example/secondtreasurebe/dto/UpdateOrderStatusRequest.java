package com.example.secondtreasurebe.dto;

import com.example.secondtreasurebe.model.Order;
import com.example.secondtreasurebe.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class UpdateOrderStatusRequest {
    private String orderId;
    private OrderStatus status;
}
