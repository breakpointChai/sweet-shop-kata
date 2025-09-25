package com.ai.sweet_shop_kata.service;

import com.ai.sweet_shop_kata.model.SweetEntity;
import com.ai.sweet_shop_kata.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SweetShopServiceTest {
    private SweetRepository sweetRepository;
    private SweetServiceImpl sweetService;

    @BeforeEach
    void setUp() {
        sweetRepository = mock(SweetRepository.class);
        sweetService = new SweetServiceImpl(sweetRepository);
    }

    @Test
    void shouldAddSweetSuccessfully() {
        SweetEntity sweet = new SweetEntity(1L, "Ladoo", "Indian", 15.0, 100, true, "Delicious ladoos");

        when(sweetRepository.save(sweet)).thenReturn(sweet);

        Sweet savedSweet = sweetShopService.addSweet(sweet);

        assertNotNull(savedSweet);
        assertEquals("Ladoo", savedSweet.getName());
        verify(sweetRepository, times(1)).save(sweet);
    }
}
