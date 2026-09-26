package com.alfredodev.miniproyectos6.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransaccionRequest(
        @NotBlank String dni,
        @NotNull @Positive Double monto,
        @NotBlank String pais,
        @NotNull LocalDateTime hora,
        @NotBlank String cliente,
        @NotBlank String canal) {
}
