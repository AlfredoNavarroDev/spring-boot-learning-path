package com.alfredodev.miniproyectos4.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.alfredodev.miniproyectos4.dto.ProductoResponse;
import com.alfredodev.miniproyectos4.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos4.service.ProductoService;

/** Testing de la capa web con @WebMvcTest + MockMvc: se mockea el service, no se levanta el contexto completo. */
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void devuelve404CuandoElProductoNoExiste() throws Exception {
        BDDMockito.given(productoService.obtener(999L)).willThrow(new ProductoNoEncontradoException(999L));

        mockMvc.perform(get("/api/productos/999")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    void devuelve400CuandoElBodyEsInvalido() throws Exception {
        String bodyInvalido = "{\"nombre\":\"\",\"categoria\":\"\",\"precio\":-1,\"stock\":-1}";

        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(bodyInvalido))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void creaUnProductoValido() throws Exception {
        String body = "{\"nombre\":\"Silla\",\"categoria\":\"Muebles\",\"precio\":199.90,\"stock\":4}";
        BDDMockito.given(productoService.crear(org.mockito.ArgumentMatchers.any()))
                .willReturn(new ProductoResponse(1L, "Silla", "Muebles", new BigDecimal("199.90"), 4));

        mockMvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.nombre", is("Silla")));
    }
}
