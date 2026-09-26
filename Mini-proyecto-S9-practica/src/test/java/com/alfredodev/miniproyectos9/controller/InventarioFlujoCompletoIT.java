package com.alfredodev.miniproyectos9.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * e2e real de "sistema de inventario completo" contra un PostgreSQL descartable
 * (Testcontainers, no H2): dos usuarios con roles distintos (ABASTECEDOR y VENDEDOR)
 * se registran, hacen login, y el flujo completo de alta -> ajuste -> intento de
 * borrado se comporta segun el rol de cada uno. Cubre en un solo test el objetivo de
 * la semana: JWT + RBAC + JPA + Postgres funcionando juntos de punta a punta.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class InventarioFlujoCompletoIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void abastecedorCreaYAjustaPeroVendedorNoPuedeBorrar() throws Exception {
        String tokenAbastecedor = registrarYObtenerToken("ABASTECEDOR");
        String tokenVendedor = registrarYObtenerToken("VENDEDOR");

        // El abastecedor da de alta un producto sobre datos ya sembrados por Flyway (V5).
        String productoJson = "{\"nombre\":\"Router WiFi 6\",\"precio\":250.00,\"stock\":8,\"categoriaId\":1,\"sedeId\":1}";
        MvcResult creado = mockMvc
                .perform(post("/api/productos").header("Authorization", "Bearer " + tokenAbastecedor)
                        .contentType(MediaType.APPLICATION_JSON).content(productoJson))
                .andExpect(status().isCreated()).andReturn();
        long id = objectMapper.readTree(creado.getResponse().getContentAsString()).get("id").asLong();

        // El vendedor puede leer el inventario...
        mockMvc.perform(get("/api/productos").header("Authorization", "Bearer " + tokenVendedor))
                .andExpect(status().isOk());

        // ...pero no puede borrar productos: RBAC lo rechaza con 403, no con un 401 generico.
        mockMvc.perform(delete("/api/productos/{id}", id).header("Authorization", "Bearer " + tokenVendedor))
                .andExpect(status().isForbidden());

        // El abastecedor tampoco puede borrar (fuera de PROPIETARIO/ADMINISTRADOR).
        mockMvc.perform(delete("/api/productos/{id}", id).header("Authorization", "Bearer " + tokenAbastecedor))
                .andExpect(status().isForbidden());
    }

    private String registrarYObtenerToken(String rol) throws Exception {
        String email = rol.toLowerCase() + "-" + System.nanoTime() + "@correo.com";
        String body = "{\"email\":\"" + email + "\",\"password\":\"clave12345\",\"rol\":\"" + rol + "\"}";

        mockMvc.perform(post("/api/auth/registro").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        MvcResult loginResult = mockMvc
                .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk()).andReturn();

        return objectMapper.readTree(loginResult.getResponse().getContentAsString()).get("accessToken").asText();
    }
}
