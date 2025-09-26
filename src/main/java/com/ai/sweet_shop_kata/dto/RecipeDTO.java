package com.ai.sweet_shop_kata.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecipeDTO {
    private List<String> ingredients;
    private List<String> instructions;
    private String cookingTime;
    private String calories;
    private String dietType;
}