package com.alfredodev.semana02practica.util;

import java.text.NumberFormat;
import java.util.Locale;

// POJO normal — sin @Component ni nada.
// No lo anotamos porque su creación depende de un parámetro (Locale)
// que controlamos desde un @Configuration centralizado (AppConfig).
//
// Spring lo crea y maneja gracias al @Bean en AppConfig.
public class PrecioFormatter {

    private final NumberFormat format;

    public PrecioFormatter(Locale locale) {
        this.format = NumberFormat.getCurrencyInstance(locale);
    }

    // TODO (Paso 6): formatear(double precio) -> String con formato moneda.
    public String formatear(double precio) {
        return format.format(precio);
    }
}
