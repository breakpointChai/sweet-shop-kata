package com.ai.sweet_shop_kata.dto;

import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class OrderDto {
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private double orderAmount;
    private String billingAddress;
    private Date orderDate;
    private String razorpayOrderId;
    private Set<OrderItemDto> items;
}
