package com.alfredodev.miniproyectos3.repositorio;

import com.alfredodev.miniproyectos3.modelo.Cliente;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogoClientesTest {

    @Test
    void buscarDevuelveOptionalPresenteOExVacio() {
        CatalogoClientes catalogo = new CatalogoClientes(Map.of("Ana", new Cliente("Ana", true)));

        assertTrue(catalogo.buscar("Ana").isPresent());
        assertTrue(catalogo.buscar("Ana").get().esVip());
        assertTrue(catalogo.buscar("Nadie").isEmpty());
    }
}
