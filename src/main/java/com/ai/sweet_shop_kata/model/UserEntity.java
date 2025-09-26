package com.ai.sweet_shop_kata.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name="users")
public class UserEntity   {

    @Id// Auto-increment ID
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password", length = 100, nullable = false)
    private String password;


    @Column(name="user_email",unique = true)
    private String email;
    private String gender;


    @Column(name = "user_image_name")
    private String imageName;

    private String role;
}
