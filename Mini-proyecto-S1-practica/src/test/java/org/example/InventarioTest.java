package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventarioTest {

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
    }

    @Test
    void agregaUnProductoNuevo() {
        Producto teclado = new Producto("P1", "Teclado mecanico", 49.99, 10);

        inventario.agregar(teclado);

        assertEquals(499.90, inventario.valorTotalInventario(), 0.001);
    }

    @Test
    void rechazaProductoConIdDuplicado() {
        inventario.agregar(new Producto("P1", "Teclado mecanico", 49.99, 10));

        assertThrows(ProductoDuplicadoException.class,
                () -> inventario.agregar(new Producto("P1", "Otro teclado", 39.99, 5)));
    }

    @Test
    void buscaProductosPorNombreParcialSinImportarMayusculas() {
        Producto teclado = new Producto("P1", "Teclado mecanico", 49.99, 10);
        Producto mouse = new Producto("P2", "Mouse inalambrico", 19.99, 20);
        inventario.agregar(teclado);
        inventario.agregar(mouse);

        List<Producto> resultado = inventario.buscarPorNombre("teclado");

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(teclado));
    }

    @Test
    void filtraProductosConStockPorDebajoDelUmbral() {
        Producto teclado = new Producto("P1", "Teclado mecanico", 49.99, 2);
        Producto mouse = new Producto("P2", "Mouse inalambrico", 19.99, 20);
        inventario.agregar(teclado);
        inventario.agregar(mouse);

        List<Producto> resultado = inventario.productosConStockBajo(5);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(teclado));
    }

    @Test
    void agrupaProductosPorRangoDePrecio() {
        Producto mouse = new Producto("P1", "Mouse", 19.99, 20);
        Producto teclado = new Producto("P2", "Teclado", 49.99, 10);
        Producto monitor = new Producto("P3", "Monitor", 249.99, 3);
        inventario.agregar(mouse);
        inventario.agregar(teclado);
        inventario.agregar(monitor);

        Map<String, List<Producto>> agrupado = inventario.agruparPorRangoDePrecio();

        assertEquals(List.of(mouse, teclado), agrupado.get("0-50"));
        assertEquals(List.of(monitor), agrupado.get("200+"));
    }
}
