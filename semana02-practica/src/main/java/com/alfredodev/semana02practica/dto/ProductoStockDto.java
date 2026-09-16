package com.alfredodev.semana02practica.dto;

// DTO = lo que el consumidor necesita VER, no la entity cruda.
// El campo `estado` ("OK"/"ALERTA"/"NO ENCONTRADO") es CALCULADO por el service:
// no existe en la entity Producto, existe solo en este DTO.
//
// Regla: el service devuelve este DTO, nunca un Producto crudo.
public record ProductoStockDto(
        String nombre,
        int stock,
        String estado
) { }
