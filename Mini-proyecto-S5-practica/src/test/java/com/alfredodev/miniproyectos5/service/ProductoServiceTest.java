package com.alfredodev.miniproyectos5.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alfredodev.miniproyectos5.domain.Categoria;
import com.alfredodev.miniproyectos5.domain.Producto;
import com.alfredodev.miniproyectos5.domain.Sede;
import com.alfredodev.miniproyectos5.dto.ProductoRequest;
import com.alfredodev.miniproyectos5.exception.CategoriaNoEncontradaException;
import com.alfredodev.miniproyectos5.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos5.repository.CategoriaRepository;
import com.alfredodev.miniproyectos5.repository.ProductoRepository;
import com.alfredodev.miniproyectos5.repository.SedeRepository;

/** Unitario puro con Mockito: sin contexto de Spring, sin base de datos. */
@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private SedeRepository sedeRepository;

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService(productoRepository, categoriaRepository, sedeRepository);
    }

    @Test
    void creaUnProductoCuandoCategoriaYSedeExisten() {
        Categoria categoria = Categoria.builder().id(1L).nombre("Perifericos").build();
        Sede sede = Sede.builder().id(1L).nombre("Central").ciudad("Lima").build();
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(sedeRepository.findById(1L)).thenReturn(Optional.of(sede));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> {
            Producto p = inv.getArgument(0);
            p.setId(10L);
            return p;
        });

        var response = productoService.crear(new ProductoRequest("Teclado", new BigDecimal("100"), 5, 1L, 1L));

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.categoria()).isEqualTo("Perifericos");
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void fallaAlCrearConCategoriaInexistente() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productoService.crear(new ProductoRequest("Teclado", new BigDecimal("100"), 5, 99L, 1L)))
                .isInstanceOf(CategoriaNoEncontradaException.class);
    }

    @Test
    void lanzaNoEncontradoAlEliminarIdInexistente() {
        when(productoRepository.existsById(404L)).thenReturn(false);

        assertThatThrownBy(() -> productoService.eliminar(404L)).isInstanceOf(ProductoNoEncontradoException.class);
    }
}
