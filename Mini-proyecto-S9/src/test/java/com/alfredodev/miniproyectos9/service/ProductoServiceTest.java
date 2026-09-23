package com.alfredodev.miniproyectos9.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alfredodev.miniproyectos9.domain.Categoria;
import com.alfredodev.miniproyectos9.domain.Producto;
import com.alfredodev.miniproyectos9.domain.Sede;
import com.alfredodev.miniproyectos9.dto.ProductoRequest;
import com.alfredodev.miniproyectos9.exception.CategoriaNoEncontradaException;
import com.alfredodev.miniproyectos9.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos9.repository.CategoriaRepository;
import com.alfredodev.miniproyectos9.repository.ProductoRepository;
import com.alfredodev.miniproyectos9.repository.SedeRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private SedeRepository sedeRepository;

    private ProductoService productoService;

    @Test
    void creaUnProductoCuandoCategoriaYSedeExisten() {
        productoService = new ProductoService(productoRepository, categoriaRepository, sedeRepository);
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

        assertThat(response.categoria()).isEqualTo("Perifericos");
    }

    @Test
    void fallaAlCrearConCategoriaInexistente() {
        productoService = new ProductoService(productoRepository, categoriaRepository, sedeRepository);
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> productoService.crear(new ProductoRequest("Teclado", new BigDecimal("100"), 5, 99L, 1L)))
                .isInstanceOf(CategoriaNoEncontradaException.class);
    }

    @Test
    void lanzaNoEncontradoAlEliminarIdInexistente() {
        productoService = new ProductoService(productoRepository, categoriaRepository, sedeRepository);
        when(productoRepository.existsById(404L)).thenReturn(false);

        assertThatThrownBy(() -> productoService.eliminar(404L)).isInstanceOf(ProductoNoEncontradoException.class);
    }
}
