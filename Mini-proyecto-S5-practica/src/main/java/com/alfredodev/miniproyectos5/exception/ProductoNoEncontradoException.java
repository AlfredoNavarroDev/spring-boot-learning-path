package com.alfredodev.miniproyectos5.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(Long id) {
        // TODO Paso 3: super(...) con un mensaje que incluya el id, ej: "No se encontro el producto con id " + id
        super((String) null);
    }
}
