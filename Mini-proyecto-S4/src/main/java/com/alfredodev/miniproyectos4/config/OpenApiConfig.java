package com.alfredodev.miniproyectos4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenApi() {
        return new OpenAPI().info(new Info().title("Inventario API - Semana 4")
                .description("CRUD en memoria del inventario, equivalente a un CRUD de NestJS con class-validator")
                .version("v1"));
    }
}
