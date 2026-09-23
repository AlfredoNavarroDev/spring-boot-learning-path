package com.alfredodev.miniproyectos4.exception;

/** 409 - se lanza cuando ya existe un producto con el mismo nombre. */
public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String nombre) {
        super("Ya existe un producto con el nombre '" + nombre + "'");
    }
}
