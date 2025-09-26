
package com.ai.sweet_shop_kata.security;

import com.ai.sweet_shop_kata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Locates the user based on the username. In our case, the username is the email.
     * @param email the email identifying the user whose data is required.
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(CustomUserDetails::new) // If user is found, wrap it in a CustomUserDetails object
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
