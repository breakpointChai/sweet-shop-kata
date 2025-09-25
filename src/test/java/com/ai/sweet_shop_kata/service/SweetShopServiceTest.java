package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetShopServiceTest {

    private SweetRepository sweetRepository;
    private SweetServiceImpl sweetService;
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        sweetRepository = mock(SweetRepository.class);
        mapper = new ModelMapper();
        sweetService = new SweetServiceImpl();
        sweetService.sweetRepository = sweetRepository; // inject mock
        sweetService.mapper = mapper; // inject real mapper
    }

    @Test
    void shouldAddSweetSuccessfully() {
        // Arrange: create DTO
        SweetDto sweetDto = new SweetDto(
                null,
                "Ladoo",
                "Delicious ladoos",
                15.0,
                "Indian",
                100,
                true,
                "image.png"
        );

        // The entity expected to be saved
        SweetEntity sweetEntity = mapper.map(sweetDto, SweetEntity.class);
        sweetEntity.setId(UUID.randomUUID().toString());

        // Mock repository save → return entity with ID
        when(sweetRepository.save(any(SweetEntity.class))).thenReturn(sweetEntity);

        // Act
        SweetDto savedSweet = sweetService.addSweet(sweetDto);

        // Assert
        assertNotNull(savedSweet);
        assertNotNull(savedSweet.getId()); // ID generated in service
        assertEquals("Ladoo", savedSweet.getName());
        assertEquals("Indian", savedSweet.getCategory());
        assertEquals(100, savedSweet.getQuantity());

        verify(sweetRepository, times(1)).save(any(SweetEntity.class));
    }



    @Test
    void testGetSweet_WhenSweetExists_ShouldReturnSweetDto() {
        // Arrange
        String sweetId = "1";
        SweetEntity sweetEntity = new SweetEntity();
        sweetEntity.setId(sweetId);
        sweetEntity.setName("Rasgulla");

        when(sweetRepository.findById(sweetId)).thenReturn(Optional.of(sweetEntity));
        when(mapper.map(sweetEntity, SweetDto.class)).thenReturn(new SweetDto()); // Mock mapping

        // Act
        SweetDto foundSweet = sweetService.getSweet(sweetId);

        // Assert
        assertNotNull(foundSweet);
        verify(sweetRepository, times(1)).findById(sweetId);
    }

    @Test
    void testGetSweet_WhenSweetDoesNotExist_ShouldThrowException() {
        // Arrange
        String sweetId = "99";
        when(sweetRepository.findById(sweetId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sweetService.getSweet(sweetId);
        });

        verify(sweetRepository, times(1)).findById(sweetId);
    }

    @Test
    void testGetSweets_ShouldReturnListOfSweetDtos() {
        // Arrange
        List<SweetEntity> sweetEntities = List.of(new SweetEntity(), new SweetEntity());
        when(sweetRepository.findAll()).thenReturn(sweetEntities);

        // Act
        List<SweetDto> sweets = sweetService.getSweets();

        // Assert
        assertEquals(2, sweets.size());
        verify(sweetRepository, times(1)).findAll();
    }
}
