package com.alfredodev.semana03.funcional;

import com.alfredodev.semana03.modelo.Cliente;
import com.alfredodev.semana03.modelo.Transaccion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterfacesFuncionalesTest {

    private final Transaccion tx =
            new Transaccion(6_000, "PE", LocalDateTime.of(2026, 9, 15, 12, 0), "Ana", "web");

    @Test
    void functionTransformaTransaccionEnCategoria() {
        assertEquals("ALTO", InterfacesFuncionales.categoriaPorMonto.apply(tx));
    }

    @Test
    void biFunctionCalculaComision() {
        assertEquals(3_000.0, InterfacesFuncionales.comision.apply(tx, 0.5));
    }

    @Test
    void supplierProduceUnaTransaccion() {
        Transaccion generada = InterfacesFuncionales.transaccionDePrueba.get();
        assertEquals(1_000.0, generada.monto());
    }

    @Test
    void consumerNoDevuelveNada() {
        // Consumer solo produce un efecto lateral; aquí verificamos que no lanza.
        InterfacesFuncionales.imprimirResumen.accept(new Cliente("Ana", true));
    }
}
