package com.ai.sweet_shop_kata.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate; // Import this

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // ADD THIS BEAN
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}