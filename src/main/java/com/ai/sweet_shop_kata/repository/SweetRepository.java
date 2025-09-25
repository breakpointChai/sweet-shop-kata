package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.SweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SweetRepository extends JpaRepository<SweetEntity,String> {

    // Method for searching sweets by name, ignoring case
    List<SweetEntity> findByNameContainingIgnoreCase(String nameKeyword);

}