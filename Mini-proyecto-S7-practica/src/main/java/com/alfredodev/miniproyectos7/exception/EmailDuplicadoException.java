package com.alfredodev.miniproyectos7.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {
        // TODO (Paso 6): pasarle a super(...) un mensaje que incluya el email duplicado,
        // ej: "Ya existe una cuenta registrada con el email '" + email + "'"
        super("TODO");
    }
}
