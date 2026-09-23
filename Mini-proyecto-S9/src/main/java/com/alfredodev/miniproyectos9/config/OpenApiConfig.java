package com.alfredodev.miniproyectos9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenApi() {
        String esquemaBearer = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Inventario API - Proyecto Final")
                        .description("Sistema de inventario completo: JWT + RBAC (5 roles) + JPA/PostgreSQL")
                        .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(esquemaBearer))
                .components(new Components().addSecuritySchemes(esquemaBearer,
                        new SecurityScheme().name(esquemaBearer).type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
