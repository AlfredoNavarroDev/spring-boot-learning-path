package com.alfredodev.miniproyectos9.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {
        // TODO Paso 2: super("Ya existe una cuenta registrada con el email '" + email + "'");
        super((String) null);
    }
}
