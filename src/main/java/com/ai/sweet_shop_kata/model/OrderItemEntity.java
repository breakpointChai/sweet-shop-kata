package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "sweet_id")
    private SweetEntity sweet;

    private int quantity;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
