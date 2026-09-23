package com.alfredodev.miniproyectos6.exception;

/** Excepcion de infraestructura: el "buro de credito" externo fallo o tardo demasiado. */
public class BuroCreditoNoDisponibleException extends RuntimeException {
    public BuroCreditoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
