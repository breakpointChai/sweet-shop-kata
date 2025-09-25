package com.ai.sweet_shop_kata.controller;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.service.SweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // update sweet
    @PutMapping("/{id}")
    public ResponseEntity<SweetDto> updateSweet(@PathVariable String id, @RequestBody SweetDto sweetDto) {
        sweetDto.setId(id);
        SweetDto updatedSweet = sweetService.updateSweet(sweetDto);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    // delete sweet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable String id) {
        sweetService.deleteSweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
