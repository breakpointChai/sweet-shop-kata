package com.ai.sweet_shop_kata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    private String categoryId;

    @NotBlank(message = "Title is required")
    @Size(min = 4, message = "Title must be at least 4 characters")
    private String title;

    private String description;
}
