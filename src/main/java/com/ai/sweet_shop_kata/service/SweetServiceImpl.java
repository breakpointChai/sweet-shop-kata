package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.dto.SweetRequestDto;
import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SweetServiceImpl implements SweetService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SweetRepository sweetRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public SweetDto addSweet(SweetRequestDto sweetRequest, MultipartFile file) throws IOException {
        SweetEntity sweetEntity = mapper.map(sweetRequest, SweetEntity.class);
        sweetEntity.setId(UUID.randomUUID().toString());

        // Upload to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "sweet_shop/sweets"));
        sweetEntity.setImageUrl((String) uploadResult.get("secure_url"));

        SweetEntity saved = sweetRepository.save(sweetEntity);
        return mapper.map(saved, SweetDto.class);
    }

    @Override
    public SweetDto updateSweet(SweetRequestDto sweetRequest, MultipartFile file) throws IOException {
        SweetEntity existing = sweetRepository.findById(sweetRequest.getId())
                .orElseThrow(() -> new RuntimeException("Sweet not found: " + sweetRequest.getId()));

        existing.setName(sweetRequest.getName());
        existing.setDescription(sweetRequest.getDescription());
        existing.setPrice(sweetRequest.getPrice());
        existing.setCategory(sweetRequest.getCategory());
        existing.setQuantity(sweetRequest.getQuantity());
        existing.setStock(sweetRequest.isStock());

        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "sweet_shop/sweets"));
            existing.setImageUrl((String) uploadResult.get("secure_url"));
        }

        SweetEntity updated = sweetRepository.save(existing);
        return mapper.map(updated, SweetDto.class);
    }

    @Override
    public void deleteSweet(String id) {
        SweetEntity sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));
        sweetRepository.delete(sweet);
    }

    @Override
    public SweetDto getSweet(String id) {
        SweetEntity sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));
        return mapper.map(sweet, SweetDto.class);
    }

    @Override
    public List<SweetDto> getSweets() {
        return sweetRepository.findAll().stream()
                .map(s -> mapper.map(s, SweetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SweetDto> searchByTitle(String title) {
        return sweetRepository.findByNameContainingIgnoreCase(title).stream()
                .map(s -> mapper.map(s, SweetDto.class))
                .collect(Collectors.toList());
    }
}
