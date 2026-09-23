package com.alfredodev.miniproyectos7.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * e2e real (sin mocks): registro -> login -> acceso a un endpoint protegido con el
 * JWT recibido. Un endpoint protegido sin token, o con token invalido, debe fallar
 * explicitamente con 401/403 - no alcanza con probar solo el camino feliz.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // H2 en memoria, no el archivo de datos de desarrollo
class AuthFlujoCompletoIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registroLoginYAccesoAEndpointProtegido() throws Exception {
        String email = "flujo-" + System.nanoTime() + "@correo.com";
        String registroJson = "{\"email\":\"" + email + "\",\"password\":\"clave12345\"}";

        mockMvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON).content(registroJson))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.email").value(email));

        MvcResult loginResult = mockMvc
                .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(registroJson))
                .andExpect(status().isOk()).andReturn();

        String token = objectMapper.readTree(loginResult.getResponse().getContentAsString()).get("accessToken")
                .asText();

        mockMvc.perform(get("/api/perfil").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }

    @Test
    void endpointProtegidoRechazaSinToken() throws Exception {
        mockMvc.perform(get("/api/perfil")).andExpect(status().isUnauthorized());
    }

    @Test
    void endpointProtegidoRechazaConTokenInvalido() throws Exception {
        mockMvc.perform(get("/api/perfil").header("Authorization", "Bearer token-basura"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginFallaConCredencialesInvalidas() throws Exception {
        String body = "{\"email\":\"no-existe@correo.com\",\"password\":\"loquesea123\"}";

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }
}
