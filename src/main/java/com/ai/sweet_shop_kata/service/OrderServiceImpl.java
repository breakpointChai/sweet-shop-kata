package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.CreateOrderRequest;
import com.ai.sweet_shop_kata.dto.OrderDto;
import com.ai.sweet_shop_kata.model.*;
import com.ai.sweet_shop_kata.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Value("${RAZORPAY_KEY}")
    private String keyId;
    @Value("${RAZORPAY_SECRET}")
    private String keySecret;

    @Override
    @Transactional
    public OrderDto createOrder(String userEmail, CreateOrderRequest request) throws RazorpayException {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getSweet().getPrice() * item.getQuantity())
                .sum();

        // 1. Create Razorpay Order
        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmount * 100); // amount in the smallest currency unit (paise)
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt_order_" + UUID.randomUUID());
        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        // 2. Create and save our local OrderEntity
        OrderEntity order = new OrderEntity();
        order.setOrderId(UUID.randomUUID().toString());
        order.setBillingAddress(request.getBillingAddress());
        order.setOrderDate(new Date());
        order.setOrderAmount(totalAmount);
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("NOT_PAID");
        order.setUser(user);
        order.setRazorpayOrderId(razorpayOrder.get("id"));

        Set<OrderItemEntity> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setSweet(cartItem.getSweet());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getSweet().getPrice() * cartItem.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toSet());

        order.setItems(orderItems);
        OrderEntity savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto capturePaymentAndUpdateOrder(String userEmail, String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        OrderEntity order = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Order not found with Razorpay order ID"));

        // Verify Razorpay signature
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);

            boolean signatureIsValid = Utils.verifyPaymentSignature(options, keySecret);

            if (signatureIsValid) {
                order.setPaymentId(razorpayPaymentId);
                order.setPaymentStatus("PAID");
                order.setOrderStatus("PLACED");

                // Clear the user's cart
                UserEntity user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                CartEntity cart = cartRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Cart not found"));
                cart.getItems().clear();
                cartRepository.save(cart);

            } else {
                order.setPaymentStatus("FAILED");
                order.setOrderStatus("FAILED");
            }
        } catch (RazorpayException e) {
            // Handle exception
            order.setPaymentStatus("FAILED");
            order.setOrderStatus("FAILED");
        }

        OrderEntity updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }
}
