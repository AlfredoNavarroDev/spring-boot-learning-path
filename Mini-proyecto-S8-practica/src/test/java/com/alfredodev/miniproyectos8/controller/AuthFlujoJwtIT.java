package com.alfredodev.miniproyectos8.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * e2e real sin mocks: registra un VENDEDOR, hace login, y confirma que el JWT
 * resultante (con el rol como claim) alcanza para GET pero es rechazado en POST
 * por @PreAuthorize - o sea, que el rol viaja correctamente del registro al token
 * y de ahi a la decision de autorizacion en cada request.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthFlujoJwtIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void elRolViajaDelRegistroAlTokenYSeRespetaEnLosEndpoints() throws Exception {
        String email = "vendedor-" + System.nanoTime() + "@correo.com";
        String registroJson = "{\"email\":\"" + email + "\",\"password\":\"clave12345\",\"rol\":\"VENDEDOR\"}";

        mockMvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON).content(registroJson))
                .andExpect(status().isCreated());

        MvcResult loginResult = mockMvc
                .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(registroJson))
                .andExpect(status().isOk()).andReturn();
        String token = objectMapper.readTree(loginResult.getResponse().getContentAsString()).get("accessToken")
                .asText();

        mockMvc.perform(get("/api/productos").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/productos").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content("{\"nombre\":\"X\",\"stock\":1}"))
                .andExpect(status().isForbidden());
    }
}
