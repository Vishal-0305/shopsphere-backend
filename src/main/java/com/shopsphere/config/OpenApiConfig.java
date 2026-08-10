package com.shopsphere.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shopSphereOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("ShopSphere API")
                        .description("REST API for ShopSphere E-Commerce Application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Vishal Choudhary")
                                .email("your-email@example.com"))
                        .license(new License()
                                .name("Apache 2.0")));
    }
}