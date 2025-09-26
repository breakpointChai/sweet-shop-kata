package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.AddItemToCartRequest;
import com.ai.sweet_shop_kata.dto.CartDto;
import com.ai.sweet_shop_kata.model.CartEntity;
import com.ai.sweet_shop_kata.model.CartItemEntity;
import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.model.UserEntity;
import com.ai.sweet_shop_kata.repository.CartItemRepository;
import com.ai.sweet_shop_kata.repository.CartRepository;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import com.ai.sweet_shop_kata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final SweetRepository sweetRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CartDto addItemToCart(String userEmail, AddItemToCartRequest request) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        SweetEntity sweet = sweetRepository.findById(request.getSweetId())
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + request.getSweetId()));

        CartEntity cart = cartRepository.findByUser(user).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setCartId(UUID.randomUUID().toString());
            newCart.setUser(user);
            return newCart;
        });

        // Check if the item is already in the cart
        CartItemEntity existingItem = cart.getItems().stream()
                .filter(item -> item.getSweet().getId().equals(request.getSweetId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setSweet(sweet);
            newItem.setQuantity(request.getQuantity());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        CartEntity savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    @Override
    public CartDto getCartForUser(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        CartEntity cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    // If user has no cart, return a new empty one without saving it
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return newCart;
                });
        return convertToDto(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(String userEmail, int cartItemId) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        CartItemEntity itemToRemove = cart.getItems().stream()
                .filter(item -> item.getCartItemId() == cartItemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart with id: " + cartItemId));

        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
        cartRepository.save(cart);
    }

    private CartDto convertToDto(CartEntity cart) {
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        cartDto.setItems(cart.getItems().stream()
                .map(item -> modelMapper.map(item, com.ai.sweet_shop_kata.dto.CartItemDto.class))
                .collect(Collectors.toSet()));

        // Calculate total price
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getSweet().getPrice() * item.getQuantity())
                .sum();
        cartDto.setTotalPrice(totalPrice);

        return cartDto;
    }
}
