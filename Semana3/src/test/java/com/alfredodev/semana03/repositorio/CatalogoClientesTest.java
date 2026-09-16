package com.alfredodev.semana03.repositorio;

import com.alfredodev.semana03.modelo.Cliente;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogoClientesTest {

    private final CatalogoClientes catalogo =
            new CatalogoClientes(Map.of("Ana", new Cliente("Ana", true)));

    @Test
    void buscarDevuelveOptionalPresenteOExVacio() {
        assertTrue(catalogo.buscar("Ana").isPresent());
        assertTrue(catalogo.buscar("Ana").get().esVip());
        assertTrue(catalogo.buscar("Nadie").isEmpty());
    }

    @Test
    void nombreDeEncadenaMap() {
        assertEquals("Ana", catalogo.nombreDe("Ana").orElseThrow());
        assertTrue(catalogo.nombreDe("Nadie").isEmpty());
    }

    @Test
    void esVipUsaOrElse() {
        assertTrue(catalogo.esVip("Ana"));
        assertFalse(catalogo.esVip("Nadie"));
    }

    @Test
    void buscarOExigirLanzaCuandoNoExiste() {
        assertEquals("Ana", catalogo.buscarOExigir("Ana").nombre());
        assertThrows(IllegalArgumentException.class, () -> catalogo.buscarOExigir("Nadie"));
    }
}
