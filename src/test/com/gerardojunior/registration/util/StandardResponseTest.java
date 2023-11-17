package com.gerardojunior.registration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StandardResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createStandardResponseWithoutPageable() {
        // Arrange
        String code = "200";
        String message = "Success";
        Object data = new Object();

        // Act
        StandardResponse standardResponse = new StandardResponse(code, message, data);

        // Assert
        assertNotNull(standardResponse);
        assertEquals(code, standardResponse.getMeta().getCode());
        assertEquals(message, standardResponse.getMeta().getMessage());
        assertEquals(data, standardResponse.getData());
        assertEquals(null, standardResponse.getMeta().getPageable());
    }

    @Test
    void createStandardResponseWithPageable() {
        // Arrange
        String code = "200";
        String message = "Success";
        Object data = new Object();
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        StandardResponse standardResponse = new StandardResponse(code, message, data, pageable);

        // Assert
        assertNotNull(standardResponse);
        assertEquals(code, standardResponse.getMeta().getCode());
        assertEquals(message, standardResponse.getMeta().getMessage());
        assertEquals(data, standardResponse.getData());
        assertEquals(pageable, standardResponse.getMeta().getPageable());
    }

    @Test
    void serializeToJson() throws Exception {
        // Arrange
        String code = "200";
        String message = "Success";
        Object data = new Object();
        StandardResponse standardResponse = new StandardResponse(code, message, data);

        // Act
        String json = objectMapper.writeValueAsString(standardResponse);

        // Assert
        assertNotNull(json);
        // Add additional assertions if needed
    }
}