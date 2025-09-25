package com.ai.sweet_shop_kata.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SweetDto {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int quantity;
    private boolean stock;
    private String imageUrl;

}


