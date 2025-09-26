package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.CartEntity;
import com.ai.sweet_shop_kata.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, String> {
    Optional<CartEntity> findByUser(UserEntity user);
}
