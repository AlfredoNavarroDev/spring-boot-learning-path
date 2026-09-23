package com.alfredodev.miniproyectos6.dto;

import java.io.Serializable;

/**
 * Serializable porque Spring Cache (Redis) necesita serializar el valor para guardarlo
 * en el store; un record ya es inmutable y cumple el contrato sin boilerplate extra.
 */
public record ScoreCrediticioResponse(String dni, int score, String nivelRiesgo, String fuente) implements Serializable {

    public static ScoreCrediticioResponse deContingencia(String dni) {
        // Fallback conservador: nivel de riesgo alto para no aprobar a ciegas cuando el
        // buro externo no responde (mejor un falso rechazo que un fraude aprobado).
        return new ScoreCrediticioResponse(dni, 0, "ALTO", "FALLBACK");
    }
}
