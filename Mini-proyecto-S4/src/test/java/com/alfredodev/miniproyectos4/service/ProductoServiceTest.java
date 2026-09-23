package com.alfredodev.miniproyectos4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alfredodev.miniproyectos4.dto.ProductoRequest;
import com.alfredodev.miniproyectos4.dto.ProductoResponse;
import com.alfredodev.miniproyectos4.exception.ProductoDuplicadoException;
import com.alfredodev.miniproyectos4.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos4.repository.ProductoRepository;

class ProductoServiceTest {

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService(new ProductoRepository());
    }

    @Test
    void creaYRecuperaUnProducto() {
        ProductoResponse creado = productoService
                .crear(new ProductoRequest("Teclado", "Perifericos", new BigDecimal("120.50"), 10));

        assertThat(creado.id()).isNotNull();
        assertThat(productoService.obtener(creado.id()).nombre()).isEqualTo("Teclado");
    }

    @Test
    void rechazaNombreDuplicado() {
        productoService.crear(new ProductoRequest("Mouse", "Perifericos", BigDecimal.TEN, 5));

        assertThatThrownBy(() -> productoService.crear(new ProductoRequest("Mouse", "Perifericos", BigDecimal.ONE, 1)))
                .isInstanceOf(ProductoDuplicadoException.class);
    }

    @Test
    void lanzaNoEncontradoParaIdInexistente() {
        assertThatThrownBy(() -> productoService.obtener(999L)).isInstanceOf(ProductoNoEncontradoException.class);
    }

    @Test
    void eliminaUnProducto() {
        ProductoResponse creado = productoService.crear(new ProductoRequest("Monitor", "Pantallas", BigDecimal.TEN, 3));

        productoService.eliminar(creado.id());

        assertThatThrownBy(() -> productoService.obtener(creado.id()))
                .isInstanceOf(ProductoNoEncontradoException.class);
    }
}
