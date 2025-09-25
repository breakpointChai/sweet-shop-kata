package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SweerServiceImpl implements SweetService {
    @Autowired
    private ModelMapper mapper;
    @Override
    public SweetDto addSweet(SweetDto sweetDto) {

        return null;
    }

    @Override
    public SweetDto updateSweet(SweetDto sweetDto) {
        return null;
    }

    @Override
    public void deleteSweet(String id) {

    }

    @Override
    public SweetDto getSweet(String id) {
        return null;
    }

    @Override
    public List<SweetDto> getSweets() {
        return List.of();
    }

    @Override
    public List<SweetDto> searchByTitle(String title) {
        return List.of();
    }
}
