package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SweetServiceImpl implements SweetService {
    @Autowired
    ModelMapper mapper;
    @Autowired
    SweetRepository sweetRepository;

    @Override
    public SweetDto addSweet(SweetDto sweetDto) {
        SweetEntity sweetEntity = mapper.map(sweetDto, SweetEntity.class);
        String id = UUID.randomUUID().toString();
        sweetEntity.setId(id);
        SweetEntity saveSweet=sweetRepository.save(sweetEntity);
        return mapper.map(saveSweet, SweetDto.class);

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
