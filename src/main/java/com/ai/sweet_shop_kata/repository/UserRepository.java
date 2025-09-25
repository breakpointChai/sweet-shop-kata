package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>
{

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndPassword(String email,String password);

    List<UserEntity> findByNameContaining(String keywords);

}