package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.dto.SweetRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SweetService {
    //add sweet
    SweetDto addSweet(SweetRequestDto sweetRequest, MultipartFile file) throws IOException;
    //update sweet
    SweetDto updateSweet(SweetRequestDto sweetRequest, MultipartFile file) throws IOException;
    //delete sweet
    void deleteSweet(String id);
    //get single sweet
    SweetDto getSweet(String id);
    //get all sweets
    List<SweetDto> getSweets();
    //get sweet by keyword
    List<SweetDto> searchByTitle(String title);
}
