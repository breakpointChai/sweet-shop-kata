package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.model.SweetEntity;
import jdk.jfr.Category;

import java.util.List;

public interface SweetService {

    //create
    SweetDto addSweet(SweetDto sweetDto);

    //update
    SweetDto updateSweet(SweetDto sweetDto);

    //delete
    void deleteSweet(String id);

    //get single sweet
    SweetDto getSweet(String id);

    //    get all sweets
    List<SweetDto> getSweets();

    //search by title
    List<SweetDto> searchByTitle(String title);






}
