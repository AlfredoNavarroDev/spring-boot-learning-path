package com.alfredodev.semana02practica.entity;

// Objeto de dominio inmutable — la "entidad" de la capa de negocio.
// No es un bean de Spring: lo crean y lo pasan los servicios.
//
// En Semana 5 (JPA) esto se convierte en una @Entity mapeada a una tabla.
// Por ahora es un record plano, igual que los DTOs de la semana 1.
public record Producto(
        String nombre,
        int stock,
        double precio
) { }
