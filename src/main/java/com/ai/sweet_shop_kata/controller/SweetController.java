package com.ai.sweet_shop_kata.controller;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.dto.SweetRequestDto;
import com.ai.sweet_shop_kata.service.SweetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/sweets")
public class SweetController {

    @Autowired
    private SweetService sweetService;

    //add sweet
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SweetDto> createSweet(
            @RequestPart("sweet") String sweetJson,
            @RequestPart("file") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        SweetRequestDto sweetRequest = mapper.readValue(sweetJson, SweetRequestDto.class);

        SweetDto createdSweet = sweetService.addSweet(sweetRequest, file);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }

    // update sweet with optional new file
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SweetDto> updateSweet(
            @PathVariable String id,
            @RequestPart("sweet") String sweetJson,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        SweetRequestDto sweetRequest = mapper.readValue(sweetJson, SweetRequestDto.class);
        sweetRequest.setId(id);

        SweetDto updatedSweet = sweetService.updateSweet(sweetRequest, file);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    //delete sweet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable String id) {
        sweetService.deleteSweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //get all sweets
    @GetMapping
    public ResponseEntity<List<SweetDto>> getAllSweets() {
        return ResponseEntity.ok(sweetService.getSweets());
    }

    //get single sweet by id
    @GetMapping("/{id}")
    public ResponseEntity<SweetDto> getSweetById(@PathVariable String id) {
        return ResponseEntity.ok(sweetService.getSweet(id));
    }

    //get sweet by search
    @GetMapping("/search")
    public ResponseEntity<List<SweetDto>> searchSweets(@RequestParam String title) {
        return ResponseEntity.ok(sweetService.searchByTitle(title));
    }
}
