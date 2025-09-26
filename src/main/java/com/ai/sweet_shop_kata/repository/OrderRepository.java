package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    // ADD THIS METHOD
    Optional<OrderEntity> findByRazorpayOrderId(String razorpayOrderId);
}
