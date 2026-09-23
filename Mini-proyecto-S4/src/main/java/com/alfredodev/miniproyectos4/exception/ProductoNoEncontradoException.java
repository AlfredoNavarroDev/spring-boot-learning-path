package com.alfredodev.miniproyectos4.exception;

/** 404 - no acoplamos la capa web a una excepcion de persistencia (JPA llega recien en la S5). */
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("No se encontro el producto con id " + id);
    }
}
