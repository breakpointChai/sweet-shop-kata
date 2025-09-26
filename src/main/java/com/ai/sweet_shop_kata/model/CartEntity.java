package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    private String cartId;

    // A cart belongs to one user
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // A cart can have multiple items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItemEntity> items = new HashSet<>();
}
