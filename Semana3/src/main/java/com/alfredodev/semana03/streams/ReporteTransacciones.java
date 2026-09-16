package com.alfredodev.semana03.streams;

import com.alfredodev.semana03.modelo.Transaccion;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stream API para lógica de negocio. La diferencia clave con los arrays de JS:
 * los streams son lazy (las operaciones intermedias no corren hasta una
 * terminal) y se consumen una vez.
 */
public final class ReporteTransacciones {

    private ReporteTransacciones() {
    }

    public static Map<String, Double> totalPorCliente(List<Transaccion> transacciones) {
        return transacciones.stream()
                .collect(Collectors.groupingBy(
                        Transaccion::cliente,
                        Collectors.summingDouble(Transaccion::monto)));
    }

    public static List<Transaccion> sobreMonto(List<Transaccion> transacciones, double montoMinimo) {
        return transacciones.stream()
                .filter(t -> t.monto() > montoMinimo)
                .toList();
    }

    public static double montoTotal(List<Transaccion> transacciones) {
        return transacciones.stream()
                .mapToDouble(Transaccion::monto)
                .sum();
    }

    /** {@code sorted}: transacciones de mayor a menor monto. */
    public static List<Transaccion> ordenarPorMontoDesc(List<Transaccion> transacciones) {
        return transacciones.stream()
                .sorted(Comparator.comparingDouble(Transaccion::monto).reversed())
                .toList();
    }

    /** {@code distinct}: nombres de cliente sin repetir, ordenados. */
    public static List<String> clientesUnicos(List<Transaccion> transacciones) {
        return transacciones.stream()
                .map(Transaccion::cliente)
                .distinct()
                .sorted()
                .toList();
    }

    /** {@code reduce}: el monto más alto del historial. */
    public static double montoMaximo(List<Transaccion> transacciones) {
        return transacciones.stream()
                .map(Transaccion::monto)
                .reduce(0.0, Double::max);
    }
}
