package com.alfredodev.miniproyectos6.motor;

import java.util.function.Predicate;

import com.alfredodev.miniproyectos6.modelo.Transaccion;

public record RegistroRegla(String nombre, Predicate<Transaccion> condicion) {
}
