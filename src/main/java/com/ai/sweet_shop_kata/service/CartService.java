package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.AddItemToCartRequest;
import com.ai.sweet_shop_kata.dto.CartDto;

public interface CartService {
    CartDto addItemToCart(String userEmail, AddItemToCartRequest request);
    CartDto getCartForUser(String userEmail);
    void removeItemFromCart(String userEmail, int cartItemId);
}
