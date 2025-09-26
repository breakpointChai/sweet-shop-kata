package com.ai.sweet_shop_kata.config;

import com.ai.sweet_shop_kata.model.UserEntity;
import com.ai.sweet_shop_kata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Default Admin Credentials
        final String adminEmail = "admin@gmail.com";
        final String adminPassword = "admin1234";

        // Check if the admin user already exists
        if (!userRepository.existsByEmail(adminEmail)) {
            UserEntity adminUser = UserEntity.builder()
                    .userId(UUID.randomUUID().toString())
                    .name("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .gender("N/A")
                    .role("ROLE_ADMIN") // Assign ADMIN role
                    .build();
            userRepository.save(adminUser);
            System.out.println("==================================================");
            System.out.println("Default Admin user created:");
            System.out.println("Email: " + adminEmail);
            System.out.println("Password: " + adminPassword);
            System.out.println("==================================================");
        }
    }
}
