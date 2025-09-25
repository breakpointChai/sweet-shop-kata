package com.ai.sweet_shop_kata.controller;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.service.SweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sweets")
public class SweetController {
    @Autowired
    SweetService sweetService;

    @PostMapping
    public ResponseEntity<SweetDto> createSweet(@RequestBody SweetDto sweetDto) {
        SweetDto createdSweet=sweetService.addSweet(sweetDto);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }


}
