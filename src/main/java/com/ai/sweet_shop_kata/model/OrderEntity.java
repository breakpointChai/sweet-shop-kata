package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String orderId;

    private String orderStatus; // PENDING, PLACED, SHIPPED, DELIVERED

    private String paymentStatus; // NOT_PAID, PAID

    private double orderAmount;

    private String billingAddress; // For simplicity, a string. Could be another entity.

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    // Razorpay specific fields
    private String razorpayOrderId;
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItemEntity> items = new HashSet<>();
}
