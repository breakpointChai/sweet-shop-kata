package com.ai.sweet_shop_kata.controller;

import com.ai.sweet_shop_kata.dto.AddItemToCartRequest;
import com.ai.sweet_shop_kata.dto.CartDto;
import com.ai.sweet_shop_kata.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<CartDto> addItemToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddItemToCartRequest request) {
        String userEmail = userDetails.getUsername();
        CartDto updatedCart = cartService.addItemToCart(userEmail, request);
        return ResponseEntity.ok(updatedCart);
    }

    // Get the current user's cart
    @GetMapping
    public ResponseEntity<CartDto> getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        CartDto cart = cartService.getCartForUser(userEmail);
        return ResponseEntity.ok(cart);
    }

    // Remove an item from the cart
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int cartItemId) {
        String userEmail = userDetails.getUsername();
        cartService.removeItemFromCart(userEmail, cartItemId);
        return ResponseEntity.ok("Item removed from cart successfully.");
    }
}
