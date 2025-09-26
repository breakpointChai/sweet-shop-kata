package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.dto.SweetDto;
import com.ai.sweet_shop_kata.dto.SweetRequestDto;
import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetShopServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private SweetServiceImpl sweetService;

    @Test
    void testAddSweet_ShouldAddSweetSuccessfully() throws IOException {
        // Arrange
        SweetRequestDto sweetRequest = new SweetRequestDto();
        sweetRequest.setName("Ladoo");
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        SweetEntity sweetEntity = new SweetEntity();
        SweetEntity savedSweetEntity = new SweetEntity();
        savedSweetEntity.setId("some-uuid");
        savedSweetEntity.setImageUrl("http://cloudinary.com/image.jpg");

        SweetDto expectedDto = new SweetDto();
        expectedDto.setId("some-uuid");

        // Mocking the chained call for Cloudinary
        Uploader uploaderMock = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploaderMock);
        when(uploaderMock.upload(any(byte[].class), any(Map.class)))
                .thenReturn(Map.of("secure_url", "http://cloudinary.com/image.jpg"));

        when(mapper.map(sweetRequest, SweetEntity.class)).thenReturn(sweetEntity);
        when(sweetRepository.save(any(SweetEntity.class))).thenReturn(savedSweetEntity);
        when(mapper.map(savedSweetEntity, SweetDto.class)).thenReturn(expectedDto);

        // Act
        SweetDto result = sweetService.addSweet(sweetRequest, mockFile);

        // Assert
        assertNotNull(result);
        assertEquals("some-uuid", result.getId());
        verify(sweetRepository, times(1)).save(any(SweetEntity.class));
        verify(cloudinary.uploader(), times(1)).upload(any(byte[].class), any(Map.class));
    }

    @Test
    void testUpdateSweet_WhenSweetExists_ShouldUpdateSuccessfully() throws IOException {
        // Arrange
        String sweetId = "1";
        SweetRequestDto sweetRequest = new SweetRequestDto();
        sweetRequest.setId(sweetId);
        sweetRequest.setName("Updated Ladoo");
        MultipartFile mockFile = new MockMultipartFile("file", "update.jpg", "image/jpeg", "update data".getBytes());

        SweetEntity existingSweet = new SweetEntity();
        existingSweet.setId(sweetId);
        SweetDto expectedDto = new SweetDto();
        expectedDto.setName("Updated Ladoo");

        when(sweetRepository.findById(sweetId)).thenReturn(Optional.of(existingSweet));
        when(sweetRepository.save(any(SweetEntity.class))).thenReturn(existingSweet);
        when(mapper.map(existingSweet, SweetDto.class)).thenReturn(expectedDto);

        // Mock Cloudinary for the update
        Uploader uploaderMock = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploaderMock);
        when(uploaderMock.upload(any(byte[].class), any(Map.class)))
                .thenReturn(Map.of("secure_url", "http://cloudinary.com/updated.jpg"));

        // Act
        SweetDto result = sweetService.updateSweet(sweetRequest, mockFile);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Ladoo", result.getName());
        verify(sweetRepository, times(1)).findById(sweetId);
        verify(sweetRepository, times(1)).save(any(SweetEntity.class));
    }

    @Test
    void testGetSweet_WhenSweetExists_ShouldReturnSweetDto() {
        // Arrange
        String sweetId = "1";
        SweetEntity sweetEntity = new SweetEntity();
        SweetDto expectedDto = new SweetDto();
        when(sweetRepository.findById(sweetId)).thenReturn(Optional.of(sweetEntity));
        when(mapper.map(sweetEntity, SweetDto.class)).thenReturn(expectedDto);

        // Act
        SweetDto result = sweetService.getSweet(sweetId);

        // Assert
        assertNotNull(result);
        verify(sweetRepository, times(1)).findById(sweetId);
    }

    @Test
    void testGetSweet_WhenSweetDoesNotExist_ShouldThrowException() {
        // Arrange
        String sweetId = "99";
        when(sweetRepository.findById(sweetId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> sweetService.getSweet(sweetId));
    }

    @Test
    void testDeleteSweet_WhenSweetExists_ShouldDelete() {
        // Arrange
        String sweetId = "1";
        SweetEntity sweetEntity = new SweetEntity();
        when(sweetRepository.findById(sweetId)).thenReturn(Optional.of(sweetEntity));
        doNothing().when(sweetRepository).delete(sweetEntity);

        // Act
        sweetService.deleteSweet(sweetId);

        // Assert
        verify(sweetRepository, times(1)).findById(sweetId);
        verify(sweetRepository, times(1)).delete(sweetEntity);
    }

    @Test
    void testGetSweets_ShouldReturnListOfSweets() {
        // Arrange
        List<SweetEntity> sweetEntities = List.of(new SweetEntity(), new SweetEntity());
        when(sweetRepository.findAll()).thenReturn(sweetEntities);

        // Act
        List<SweetDto> result = sweetService.getSweets(); // Corrected method name

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(sweetRepository, times(1)).findAll();
    }

    @Test
    void testSearchByTitle_ShouldReturnMatchingSweets() {
        // Arrange
        String keyword = "Ladoo";
        List<SweetEntity> foundEntities = List.of(new SweetEntity());
        when(sweetRepository.findByNameContainingIgnoreCase(keyword)).thenReturn(foundEntities);

        // Act
        List<SweetDto> result = sweetService.searchByTitle(keyword);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sweetRepository, times(1)).findByNameContainingIgnoreCase(keyword);
    }
}