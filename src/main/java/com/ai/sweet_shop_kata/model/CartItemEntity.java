package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    // An item belongs to one sweet
    @ManyToOne
    @JoinColumn(name = "sweet_id")
    private SweetEntity sweet;

    private int quantity;

    // An item belongs to one cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
}
