package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductoTest {

    @Test
    void creaProductoConDatosValidos() {
        Producto producto = new Producto("P1", "Teclado", 49.99, 10);

        assertEquals("P1", producto.id());
        assertEquals("Teclado", producto.nombre());
        assertEquals(49.99, producto.precio());
        assertEquals(10, producto.stock());
    }

    @Test
    void rechazaStockNegativo() {
        assertThrows(StockNegativoException.class,
                () -> new Producto("P1", "Teclado", 49.99, -1));
    }
}
