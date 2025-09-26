package com.ai.sweet_shop_kata.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC ENDPOINTS: Anyone can access these
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                        // 2. AUTHENTICATED ENDPOINTS: Any logged-in user (Admin or User) can access
                        .requestMatchers(HttpMethod.GET, "/api/sweets/**").authenticated()

                        // 3. ROLE_USER ENDPOINTS: Only users with the "USER" role
                        .requestMatchers("/api/cart/**").hasRole("USER")
                        .requestMatchers("/api/orders/**").hasRole("USER")

                        // 4. ROLE_ADMIN ENDPOINTS: Only users with the "ADMIN" role
                        .requestMatchers("/api/ai/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/sweets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/sweets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/sweets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                        // 5. DEFAULT RULE: Any other request requires authentication
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

