package com.ai.sweet_shop_kata.controller;

import com.ai.sweet_shop_kata.dto.CreateOrderRequest;
import com.ai.sweet_shop_kata.dto.OrderDto;
import com.ai.sweet_shop_kata.service.OrderService;
import com.razorpay.RazorpayException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateOrderRequest request) throws RazorpayException {
        String userEmail = userDetails.getUsername();
        OrderDto order = orderService.createOrder(userEmail, request);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/capture")
    public ResponseEntity<OrderDto> capturePayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> paymentDetails) {
        String userEmail = userDetails.getUsername();
        String razorpayOrderId = paymentDetails.get("razorpay_order_id");
        String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
        String razorpaySignature = paymentDetails.get("razorpay_signature");

        OrderDto updatedOrder = orderService.capturePaymentAndUpdateOrder(
                userEmail, razorpayOrderId, razorpayPaymentId, razorpaySignature
        );
        return ResponseEntity.ok(updatedOrder);
    }
}
