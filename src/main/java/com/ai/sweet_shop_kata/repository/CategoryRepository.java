package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
