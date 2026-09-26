package com.alfredodev.miniproyectos4.exception;

/** 404 - no acoplamos la capa web a una excepcion de persistencia (JPA llega recien en la S5). */
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        // TODO (Paso 2): pasarle a super(...) un mensaje que incluya el id buscado,
        // ej: "No se encontro el producto con id " + id
        super("TODO");
    }
}
