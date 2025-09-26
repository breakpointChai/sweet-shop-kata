package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);
    void deleteCategory(String categoryId);
    CategoryDto getCategory(String categoryId);
    List<CategoryDto> getCategories();
}
