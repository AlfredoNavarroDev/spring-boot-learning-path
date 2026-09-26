package com.alfredodev.miniproyectos5.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.alfredodev.miniproyectos5.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos5.service.ProductoService;

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
}
