package com.gerardojunior.registration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OpenAPIConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void testOpenAPIConfiguration() {
        // Verifica se a configuração da documentação OpenAPI está correta

        // Verifica o título da API
        Info info = openAPI.getInfo();
        assertEquals("Demo Service API", info.getTitle());

        // Verifica a versão da API
        assertEquals("1.0", info.getVersion());

        // Verifica a descrição da API
        assertEquals("This API exposes endpoints to manage demo.", info.getDescription());

        // Verifica o contato
        assertEquals("me@gerardo-junior.com", info.getContact().getEmail());
        assertEquals("Gerardo Junior", info.getContact().getName());
        assertEquals("https://gerardo-junior.com", info.getContact().getUrl());

        // Verifica as informações da licença
        assertEquals("MIT License", info.getLicense().getName());
        assertEquals("https://choosealicense.com/licenses/mit/", info.getLicense().getUrl());

        // Verifica os servidores
        assertEquals(2, openAPI.getServers().size());
        Server devServer = openAPI.getServers().get(0);
        Server prodServer = openAPI.getServers().get(1);
        assertEquals("http://localhost:8080", devServer.getUrl());
        assertEquals("Server URL in Development environment", devServer.getDescription());
        assertEquals("http://api.example.com", prodServer.getUrl());
        assertEquals("Server URL in Production environment", prodServer.getDescription());
    }
}
