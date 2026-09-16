package com.alfredodev.semana03.repositorio;

import com.alfredodev.semana03.modelo.Cliente;

import java.util.Map;
import java.util.Optional;

/**
 * Catálogo en memoria de clientes conocidos.
 *
 * Demuestra la regla de oro de {@code Optional}: se usa SOLO como valor de
 * retorno (nunca como campo). Hay varios puntos de retorno que devuelven
 * {@code Optional} y encadenan {@code map}/{@code orElse}/{@code orElseThrow}
 * en vez de {@code null} + {@code if (x != null)}.
 */
public class CatalogoClientes {

    private final Map<String, Cliente> porNombre;

    public CatalogoClientes(Map<String, Cliente> porNombre) {
        this.porNombre = Map.copyOf(porNombre);
    }

    /** Punto 1: retorna el cliente, o {@code Optional.empty()} si no existe. */
    public Optional<Cliente> buscar(String nombre) {
        return Optional.ofNullable(porNombre.get(nombre));
    }

    /** Punto 2: encadena {@code map} para transformar el cliente en su nombre. */
    public Optional<String> nombreDe(String nombre) {
        return buscar(nombre).map(Cliente::nombre);
    }

    /** Punto 3: {@code map} + {@code orElse} para dar un valor por defecto. */
    public boolean esVip(String nombre) {
        return buscar(nombre).map(Cliente::esVip).orElse(false);
    }

    /** Punto 4: {@code orElseThrow} cuando la ausencia es un error real. */
    public Cliente buscarOExigir(String nombre) {
        return buscar(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + nombre));
    }
}
