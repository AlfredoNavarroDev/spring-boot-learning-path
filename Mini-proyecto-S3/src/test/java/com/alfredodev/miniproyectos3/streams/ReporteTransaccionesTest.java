package com.alfredodev.miniproyectos3.streams;

import com.alfredodev.miniproyectos3.modelo.Transaccion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReporteTransaccionesTest {

    @Test
    void totalPorClienteAgrupaYSuma() {
        List<Transaccion> txs = List.of(
                tx(100, "Ana"),
                tx(200, "Ana"),
                tx(50, "Luis"));

        Map<String, Double> totales = ReporteTransacciones.totalPorCliente(txs);

        assertEquals(300.0, totales.get("Ana"));
        assertEquals(50.0, totales.get("Luis"));
    }

    @Test
    void montoTotalSumaTodo() {
        List<Transaccion> txs = List.of(tx(100, "Ana"), tx(200, "Luis"));

        assertEquals(300.0, ReporteTransacciones.montoTotal(txs));
    }

    private Transaccion tx(double monto, String cliente) {
        return new Transaccion(monto, "PE", LocalDateTime.of(2026, 9, 15, 12, 0), cliente, "web");
    }
}
