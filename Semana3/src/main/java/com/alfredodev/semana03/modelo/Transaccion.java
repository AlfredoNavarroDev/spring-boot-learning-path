package com.alfredodev.semana03.modelo;

import java.time.LocalDateTime;

/**
 * Objeto de dominio sobre el que corren las reglas de fraude.
 * Inmutable por diseño (record) — igual que un DTO readonly en TS.
 */
public record Transaccion(
        double monto,
        String pais,
        LocalDateTime hora,
        String cliente,
        String canal) {
}
