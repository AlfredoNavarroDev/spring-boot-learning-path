package com.alfredodev.miniproyectos9.dto;

import com.alfredodev.miniproyectos9.domain.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank @Email(message = "El email no tiene un formato valido") String email,
        @NotBlank @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres") String password,
        @NotNull(message = "El rol es obligatorio") Rol rol) {
}
