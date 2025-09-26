package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.UserRegisterRequest;
import com.ai.sweet_shop_kata.model.UserEntity;
import com.ai.sweet_shop_kata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        UserEntity user = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .gender(registerRequest.getGender())
                .role("ROLE_USER") // Assign USER role by default
                .build();

        userRepository.save(user);
    }
}
