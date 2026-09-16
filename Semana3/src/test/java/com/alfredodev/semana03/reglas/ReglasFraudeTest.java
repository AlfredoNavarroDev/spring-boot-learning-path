package com.alfredodev.semana03.reglas;

import com.alfredodev.semana03.modelo.Transaccion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReglasFraudeTest {

    @Test
    void montoSobreLimite() {
        var regla = ReglasFraude.montoSobreLimite();
        assertTrue(regla.condicion().test(tx(10_001, "PE", "12:00")));
        assertFalse(regla.condicion().test(tx(10_000, "PE", "12:00")));
    }

    @Test
    void paisBloqueado() {
        var regla = ReglasFraude.paisBloqueado();
        assertTrue(regla.condicion().test(tx(100, "PRK", "12:00")));
        assertFalse(regla.condicion().test(tx(100, "PE", "12:00")));
    }

    @Test
    void horarioInusual() {
        var regla = ReglasFraude.horarioInusual();
        assertTrue(regla.condicion().test(tx(100, "PE", "03:00")));
        assertFalse(regla.condicion().test(tx(100, "PE", "12:00")));
    }

    @Test
    void velocityCheckDisparaConCuatroTransaccionesEnUnMinuto() {
        List<Transaccion> historial = List.of(
                tx(100, "PE", "10:00:00"),
                tx(100, "PE", "10:00:15"),
                tx(100, "PE", "10:00:30"),
                tx(100, "PE", "10:00:45"));

        var regla = ReglasFraude.velocityCheck(historial);

        assertTrue(regla.condicion().test(historial.get(0)));
    }

    @Test
    void velocityCheckNoDisparaConTresTransacciones() {
        List<Transaccion> historial = List.of(
                tx(100, "PE", "10:00:00"),
                tx(100, "PE", "10:00:15"),
                tx(100, "PE", "10:00:30"));

        var regla = ReglasFraude.velocityCheck(historial);

        assertFalse(regla.condicion().test(historial.get(0)));
    }

    private Transaccion tx(double monto, String pais, String hora) {
        return new Transaccion(monto, pais, LocalDateTime.parse("2026-09-15T" + hora), "Ana", "web");
    }
}
