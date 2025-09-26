package com.ai.sweet_shop_kata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SweetRequestDto {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean stock;
    private String categoryId;
}
