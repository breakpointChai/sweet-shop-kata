package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
}
