package com.ai.sweet_shop_kata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;

    private String name;


    private String email;

    private String password;

    private String gender;


    private String imageName;

    private String role;
}
