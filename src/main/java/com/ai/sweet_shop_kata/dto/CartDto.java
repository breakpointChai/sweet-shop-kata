package com.ai.sweet_shop_kata.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private String cartId;
    private Set<CartItemDto> items = new HashSet<>();
    private double totalPrice;
}
