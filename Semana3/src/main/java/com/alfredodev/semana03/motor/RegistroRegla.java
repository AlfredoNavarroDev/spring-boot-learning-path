package com.alfredodev.semana03.motor;

import com.alfredodev.semana03.modelo.Transaccion;

import java.util.function.Predicate;

/**
 * Una regla de validación: un nombre legible asociado a un {@code Predicate}.
 */
public record RegistroRegla(String nombre, Predicate<Transaccion> condicion) {
}
