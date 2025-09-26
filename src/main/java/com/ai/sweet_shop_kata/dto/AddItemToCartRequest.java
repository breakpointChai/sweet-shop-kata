package com.ai.sweet_shop_kata.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddItemToCartRequest {

    @NotBlank(message = "Sweet ID is required")
    private String sweetId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
