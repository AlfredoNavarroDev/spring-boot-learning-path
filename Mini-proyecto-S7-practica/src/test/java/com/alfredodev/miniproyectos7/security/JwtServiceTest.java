package com.alfredodev.miniproyectos7.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(
            new JwtProperties("clave-de-pruebas-de-al-menos-32-bytes-de-largo", 30));

    @Test
    void generaUnTokenDelQueSePuedeExtraerElEmail() {
        String token = jwtService.generarAccessToken("ana@correo.com", "CLIENTE");

        assertThat(jwtService.extraerEmail(token)).isEqualTo("ana@correo.com");
        assertThat(jwtService.esValido(token, "ana@correo.com")).isTrue();
    }

    @Test
    void unTokenNoEsValidoParaOtroEmail() {
        String token = jwtService.generarAccessToken("ana@correo.com", "CLIENTE");

        assertThat(jwtService.esValido(token, "otro@correo.com")).isFalse();
    }
}
