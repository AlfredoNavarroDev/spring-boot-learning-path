package com.alfredodev.miniproyectos8.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {
        // TODO Paso 3: super(...) con un mensaje que incluya el email, ej:
        // "Ya existe una cuenta registrada con el email '" + email + "'"
        super((String) null);
    }
}
