//package com.ai.sweet_shop_kata.securtiy;
//
//import com.ai.sweet_shop_kata.model.UserEntity;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import java.util.Collection;
//import java.util.List;
//
//@AllArgsConstructor
//public class CustomUserDetails implements UserDetails {
//
//    private final UserEntity user;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(); // add roles/authorities later if needed
//    }
//
//    @Override
//    public String getPassword() { return user.getPassword(); }
//
//    @Override
//    public String getUsername() { return user.getEmail(); }
//
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//    @Override public boolean isEnabled() { return true; }
//
//    // convenience
//    public String getId() { return user.getUserId(); }
//    public String getName() { return user.getName(); }
//}
package com.ai.sweet_shop_kata.security;

import com.ai.sweet_shop_kata.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This is where we provide the user's role to Spring Security.
        // The role from your UserEntity (e.g., "ROLE_ADMIN") is converted into a GrantedAuthority.
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // We use the email as the unique username for authentication.
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
