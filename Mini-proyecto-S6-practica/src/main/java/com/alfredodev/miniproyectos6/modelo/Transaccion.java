package com.alfredodev.miniproyectos6.modelo;

import java.time.LocalDateTime;

/** Portado de la Semana 3: objeto de dominio sobre el que corren las reglas de fraude. */
public record Transaccion(double monto, String pais, LocalDateTime hora, String cliente, String canal) {
}
