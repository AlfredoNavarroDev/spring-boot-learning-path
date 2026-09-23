package com.alfredodev.miniproyectos8.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * Matriz de RBAC: cada request se autentica puntualmente con
 * SecurityMockMvcRequestPostProcessors.user(...).roles(...), sin pasar por login real
 * (eso ya lo cubre el flujo completo probado manualmente en la Semana 7/8 con curl).
 * Cada regla amplia (multiples roles permitidos) se prueba con un caso permitido y un
 * caso explicitamente denegado -> un endpoint protegido sin el rol correcto debe
 * fallar con 403, no solo el camino feliz debe estar cubierto.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductoRbacIT {

    @Autowired
    private MockMvc mockMvc;

    private static final String PRODUCTO_JSON = "{\"nombre\":\"Taladro\",\"stock\":10}";
    private static final String STOCK_JSON = "{\"stock\":5}";

    @Test
    void cualquierRolAutenticadoPuedeListar() throws Exception {
        mockMvc.perform(get("/api/productos").with(user("vendedor").roles("VENDEDOR"))).andExpect(status().isOk());
    }

    @Test
    void sinAutenticarNoPuedeListar() throws Exception {
        mockMvc.perform(get("/api/productos")).andExpect(status().isUnauthorized());
    }

    @Test
    void abastecedorPuedeCrearProductos() throws Exception {
        mockMvc.perform(crear().with(user("abastecedor").roles("ABASTECEDOR"))).andExpect(status().isCreated());
    }

    @Test
    void vendedorNoPuedeCrearProductos() throws Exception {
        mockMvc.perform(crear().with(user("vendedor").roles("VENDEDOR"))).andExpect(status().isForbidden());
    }

    @Test
    void tecnicoPuedeAjustarStock() throws Exception {
        Long id = crearProductoYObtenerId();

        mockMvc.perform(patch("/api/productos/{id}/stock", id).contentType(MediaType.APPLICATION_JSON)
                .content(STOCK_JSON).with(user("tecnico").roles("TECNICO"))).andExpect(status().isOk());
    }

    @Test
    void vendedorNoPuedeAjustarStock() throws Exception {
        mockMvc.perform(patch("/api/productos/{id}/stock", 999L).contentType(MediaType.APPLICATION_JSON)
                .content(STOCK_JSON).with(user("vendedor").roles("VENDEDOR"))).andExpect(status().isForbidden());
    }

    @Test
    void propietarioPuedeEliminar() throws Exception {
        Long id = crearProductoYObtenerId();

        mockMvc.perform(delete("/api/productos/{id}", id).with(user("propietario").roles("PROPIETARIO")))
                .andExpect(status().isNoContent());
    }

    @Test
    void tecnicoNoPuedeEliminar() throws Exception {
        mockMvc.perform(delete("/api/productos/{id}", 999L).with(user("tecnico").roles("TECNICO")))
                .andExpect(status().isForbidden());
    }

    private Long crearProductoYObtenerId() throws Exception {
        String contenido = mockMvc.perform(crear().with(user("abastecedor").roles("ABASTECEDOR"))).andReturn()
                .getResponse().getContentAsString();
        return Long.valueOf(contenido.replaceAll(".*\"id\":(\\d+).*", "$1"));
    }

    private MockHttpServletRequestBuilder crear() {
        return post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(PRODUCTO_JSON);
    }
}
