package com.alfredodev.miniproyectos9.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(Long id) {
        // TODO Paso 2: super("No se encontro el producto con id " + id);
        super((String) null);
    }
}
