package com.alfredodev.miniproyectos4.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de dominio en memoria (todavia sin JPA, eso llega en la Semana 5).
 * Equivalente a la entity de TypeORM, pero guardada en un Map en vez de una tabla.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Integer stock;
}
