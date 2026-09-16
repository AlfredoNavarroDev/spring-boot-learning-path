package com.alfredodev.semana02practica.config;

import com.alfredodev.semana02practica.util.PrecioFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Locale;

// @Configuration = "esta clase define @Beans".
// Usás @Bean (no @Component) cuando NO podés anotar la clase:
//   1. Es de una librería externa / del JDK (java.time.Clock).
//   2. Necesita parámetros de inicialización (PrecioFormatter + Locale).
// En NestJS esto equivale a useFactory/useValue en un @Module().
@Configuration
public class AppConfig {

    // TODO (Paso 6): registrá un @Bean Clock que devuelva Clock.systemDefaultZone().
    //   (java.time.Clock es del JDK — imposible ponerle @Component, por eso @Bean.)
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    // TODO (Paso 6): registrá un @Bean PrecioFormatter que reciba por @Value
    //   inventario.moneda.language e inventario.moneda.country y construya
    //   new PrecioFormatter(new Locale(language, country)).
    //   (PrecioFormatter es un POJO sin anotación; lo crea este @Bean.)
    @Bean
    public PrecioFormatter precioFormatter(
            @Value("${inventario.moneda.language}") String language,
            @Value("${inventario.moneda.country}")  String country
    ) {
        return new PrecioFormatter(new Locale(language, country));
    }
}
