package com.ai.sweet_shop_kata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int price;
    private int quantity;
    private boolean stock;
    @Column(length = 10000)
    private String description;
    private String imageUrl;

}
