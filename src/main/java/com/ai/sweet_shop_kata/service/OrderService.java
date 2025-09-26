package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.CreateOrderRequest;
import com.ai.sweet_shop_kata.dto.OrderDto;
import com.razorpay.RazorpayException;

public interface OrderService {
    OrderDto createOrder(String userEmail, CreateOrderRequest request) throws RazorpayException;
    OrderDto capturePaymentAndUpdateOrder(String userEmail, String razorpayOrderId, String razorpayPaymentId, String razorpaySignature);
}
