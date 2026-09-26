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
        // TODO (Paso 6): agrupá por Transaccion::cliente y sumá los montos con
        // Collectors.groupingBy(Transaccion::cliente, Collectors.summingDouble(Transaccion::monto)).
        throw new UnsupportedOperationException("TODO Paso 6: ReporteTransacciones.totalPorCliente");
    }

    public static List<Transaccion> sobreMonto(List<Transaccion> transacciones, double montoMinimo) {
        // TODO (Paso 6): filtrá las transacciones con monto() > montoMinimo y
        // devolvé la lista con .toList().
        throw new UnsupportedOperationException("TODO Paso 6: ReporteTransacciones.sobreMonto");
    }

    public static double montoTotal(List<Transaccion> transacciones) {
        // TODO (Paso 6): mapeá a montos con mapToDouble(Transaccion::monto) y
        // sumá con .sum().
        throw new UnsupportedOperationException("TODO Paso 6: ReporteTransacciones.montoTotal");
    }
}
