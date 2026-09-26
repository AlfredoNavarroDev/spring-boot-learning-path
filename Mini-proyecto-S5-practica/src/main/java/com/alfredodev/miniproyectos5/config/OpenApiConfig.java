package com.alfredodev.miniproyectos5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
// (ya completo — solo builder de metadata Swagger)
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Inventario API - Semana 5").description("CRUD con Spring Data JPA + PostgreSQL")
                        .version("v1"));
    }
}
