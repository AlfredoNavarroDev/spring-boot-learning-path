package com.alfredodev.miniproyectos3.streams;

import com.alfredodev.miniproyectos3.modelo.Transaccion;

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
}
