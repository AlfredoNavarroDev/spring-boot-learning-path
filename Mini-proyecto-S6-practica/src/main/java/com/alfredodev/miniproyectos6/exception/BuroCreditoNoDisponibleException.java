package com.alfredodev.miniproyectos6.exception;

/** Excepcion de infraestructura: el "buro de credito" externo fallo o tardo demasiado. */
public class BuroCreditoNoDisponibleException extends RuntimeException {
    public BuroCreditoNoDisponibleException(String mensaje) {
        // TODO (Paso 6): pasarle `mensaje` a super(...) tal cual.
        super("TODO");
    }
}
