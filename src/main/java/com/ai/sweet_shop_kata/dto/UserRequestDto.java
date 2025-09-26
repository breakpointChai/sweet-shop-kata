package com.ai.sweet_shop_kata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String userId;

    private String name;


    private String email;

    private String password;

    private String gender;

}
