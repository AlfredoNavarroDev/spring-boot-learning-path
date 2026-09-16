package com.alfredodev.semana03.modelo;

/**
 * Detalle de un cliente del catálogo. No es la fuente de la transacción
 * (ahí solo va el nombre), sino el registro que se busca con {@code Optional}.
 */
public record Cliente(String nombre, boolean esVip) {
}
