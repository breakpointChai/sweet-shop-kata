package com.ai.sweet_shop_kata.repository;

import com.ai.sweet_shop_kata.model.SweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<SweetEntity,String> {

}
