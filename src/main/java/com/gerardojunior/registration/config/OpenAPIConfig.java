package com.gerardojunior.registration.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class  OpenAPIConfig {

    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;

    @Value("${bezkoder.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("me@gerardo-junior.com");
        contact.setName("Gerardo Junior");
        contact.setUrl("https://gerardo-junior.com");

        Info info = new Info().title("Demo Service API")
                              .version("1.0")
                              .contact(contact)
                              .description("This API exposes endpoints to manage demo.")
                              .termsOfService("https://gerardo-junior.com")
                              .license(new License().name("MIT License")
                                                    .url("https://choosealicense.com/licenses/mit/"));

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
