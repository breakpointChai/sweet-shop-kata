package com.ai.sweet_shop_kata.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "Billing address is required")
    private String billingAddress;
}
