package com.gerardojunior.registration.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Test
    void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testUser", password = "password", roles = "USER")
    void testAuthenticatedAccess() throws Exception {
        // Test authenticated access, adjust as needed
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/some-protected-endpoint"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Add more tests as needed for your specific use cases
}