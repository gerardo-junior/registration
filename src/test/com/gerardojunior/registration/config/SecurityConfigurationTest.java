package com.gerardojunior.registration.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class SecurityConfigurationTest {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void testSecurityConfiguration() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup()
                                         .apply(securityFilterChain)
                                         .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }
}