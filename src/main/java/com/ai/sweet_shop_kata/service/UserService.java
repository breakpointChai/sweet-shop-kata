package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.UserRegisterRequest;

public interface UserService {
    void registerUser(UserRegisterRequest registerRequest);
}
