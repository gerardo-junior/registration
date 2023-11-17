package com.gerardojunior.registration.config;

import com.gerardojunior.registration.config.ApplicationConfig;
import com.gerardojunior.registration.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationConfigTest {

    @Test
    void testUserDetailsService() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByEmail("existingUser")).thenReturn(/* mock your user here */);

        ApplicationConfig config = new ApplicationConfig(repository);
        InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager) config.userDetailsService();
        assertNotNull(userDetailsManager.loadUserByUsername("existingUser"));

        assertThrows(UsernameNotFoundException.class, () -> userDetailsManager.loadUserByUsername("nonExistingUser"));
    }

    @Test
    void testAuthenticationProvider() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByEmail("existingUser")).thenReturn(/* mock your user here */);

        ApplicationConfig config = new ApplicationConfig(repository);
        assertTrue(config.authenticationProvider() instanceof AuthenticationProvider);
    }

    @Test
    void testPasswordEncoder() {
        ApplicationConfig config = new ApplicationConfig(/* mock your UserRepository here */);
        assertTrue(config.passwordEncoder() instanceof BCryptPasswordEncoder);
    }
}
