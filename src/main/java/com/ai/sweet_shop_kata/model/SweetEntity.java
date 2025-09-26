package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sweets_shop")
public class SweetEntity {

    @Id
    private String id;
    private String name;
    private String category;
    private double price; // Corrected from int to double
    private int quantity;
    private boolean stock;
    @Column(length = 10000)
    private String description;
    private String imageUrl;
    // It links this Sweet to a Category.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;
}