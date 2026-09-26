package com.alfredodev.miniproyectos4.exception;

/** 409 - se lanza cuando ya existe un producto con el mismo nombre. */
public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String nombre) {
        // TODO (Paso 2): pasarle a super(...) un mensaje que incluya el nombre duplicado,
        // ej: "Ya existe un producto con el nombre '" + nombre + "'"
        super("TODO");
    }
}
