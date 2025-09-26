package com.ai.sweet_shop_kata.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private int cartItemId;
    private SweetDto sweet;
    private int quantity;
}
