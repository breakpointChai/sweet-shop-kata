package com.ai.sweet_shop_kata.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private int orderItemId;
    private SweetDto sweet;
    private int quantity;
    private double totalPrice;
}
