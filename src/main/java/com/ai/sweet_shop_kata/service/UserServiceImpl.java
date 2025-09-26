package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.UserDto;
import com.ai.sweet_shop_kata.exceptions.ResourceNotFoundException;
import com.ai.sweet_shop_kata.model.UserEntity;
import com.ai.sweet_shop_kata.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Generate unique ID for the user
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setRole("ROLE_USER");
        // Convert DTO to entity
        UserEntity user = mapper.map(userDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // Save the user and convert back to DTO
        UserEntity savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        // Update user properties
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        // Save updated user and convert to DTO
        UserEntity updatedUser = userRepository.save(user);
        return mapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        // Delete the user from repository
        userRepository.delete(user);
    }
    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> users = userRepository.findAll();  // Fetch all users from the repository
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))  // Convert each User entity to UserDto
                .collect(Collectors.toList());  // Collect them into a list and return
    }


    @Override
    public UserDto getUserById(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given email id"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<UserEntity> users = userRepository.findByNameContaining(keyword);
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
