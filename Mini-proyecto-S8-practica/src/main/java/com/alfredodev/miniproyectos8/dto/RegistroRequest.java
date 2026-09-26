package com.alfredodev.miniproyectos8.dto;

import com.alfredodev.miniproyectos8.domain.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank @Email(message = "El email no tiene un formato valido") String email,
        @NotBlank @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres") String password,
        // Boolean/wrapper explicito, no un default silencioso: el rol de un usuario en un
        // sistema de inventario real nunca deberia asumirse (ver skill spring-boot-pro, DTOs).
        @NotNull(message = "El rol es obligatorio") Rol rol) {
}
