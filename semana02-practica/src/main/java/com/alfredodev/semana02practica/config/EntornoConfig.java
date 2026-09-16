package com.alfredodev.semana02practica.config;

// Value object que describe el entorno activo.
// No es un @Component: lo crea PerfilConfig vía @Bean, condicionado por @Profile.
public record EntornoConfig(
        String nombre,
        String descripcion
) { }
