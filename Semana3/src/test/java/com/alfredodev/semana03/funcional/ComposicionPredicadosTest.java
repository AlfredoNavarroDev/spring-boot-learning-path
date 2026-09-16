package com.alfredodev.semana03.funcional;

import com.alfredodev.semana03.modelo.Cliente;
import com.alfredodev.semana03.modelo.Transaccion;
import com.alfredodev.semana03.repositorio.CatalogoClientes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComposicionPredicadosTest {

    private Transaccion tx(double monto, String pais, int hora, String cliente, String canal) {
        return new Transaccion(monto, pais, LocalDateTime.of(2026, 9, 15, hora, 0), cliente, canal);
    }

    @Test
    void cincoPredicadosSimples() {
        assertTrue(ComposicionPredicados.montoAlto().test(tx(6_000, "PE", 12, "Ana", "web")));
        assertFalse(ComposicionPredicados.montoAlto().test(tx(4_000, "PE", 12, "Ana", "web")));

        assertTrue(ComposicionPredicados.paisBloqueado().test(tx(100, "PRK", 12, "Ana", "web")));
        assertFalse(ComposicionPredicados.paisBloqueado().test(tx(100, "PE", 12, "Ana", "web")));

        assertTrue(ComposicionPredicados.horarioInusual().test(tx(100, "PE", 3, "Ana", "web")));
        assertFalse(ComposicionPredicados.horarioInusual().test(tx(100, "PE", 12, "Ana", "web")));

        assertTrue(ComposicionPredicados.clienteEs("Ana").test(tx(100, "PE", 12, "Ana", "web")));
        assertFalse(ComposicionPredicados.clienteEs("Ana").test(tx(100, "PE", 12, "Luis", "web")));

        assertTrue(ComposicionPredicados.canalEs("web").test(tx(100, "PE", 12, "Ana", "web")));
        assertFalse(ComposicionPredicados.canalEs("web").test(tx(100, "PE", 12, "Ana", "app")));
    }

    @Test
    void combinacionAnd() {
        assertTrue(ComposicionPredicados.montoAltoEnWeb().test(tx(6_000, "PE", 12, "Ana", "web")));
        assertFalse(ComposicionPredicados.montoAltoEnWeb().test(tx(6_000, "PE", 12, "Ana", "app")));
    }

    @Test
    void combinacionOr() {
        assertTrue(ComposicionPredicados.riesgoOperativo().test(tx(100, "PRK", 12, "Ana", "web")));
        assertTrue(ComposicionPredicados.riesgoOperativo().test(tx(100, "PE", 3, "Ana", "web")));
        assertFalse(ComposicionPredicados.riesgoOperativo().test(tx(100, "PE", 12, "Ana", "web")));
    }

    @Test
    void combinacionOrNegateExcluyeVip() {
        CatalogoClientes catalogo = new CatalogoClientes(Map.of(
                "María", new Cliente("María", true),
                "Carlos", new Cliente("Carlos", false)));

        // Carlos: no VIP y monto alto → dispara la alerta.
        assertTrue(ComposicionPredicados.alertaManual(catalogo).test(tx(6_000, "PE", 12, "Carlos", "web")));
        // María: VIP con el mismo monto → no dispara.
        assertFalse(ComposicionPredicados.alertaManual(catalogo).test(tx(6_000, "PE", 12, "María", "web")));
    }
}
