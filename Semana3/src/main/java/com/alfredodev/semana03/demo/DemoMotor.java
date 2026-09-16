package com.alfredodev.semana03.demo;

import com.alfredodev.semana03.modelo.Cliente;
import com.alfredodev.semana03.modelo.Transaccion;
import com.alfredodev.semana03.motor.MotorValidacion;
import com.alfredodev.semana03.reglas.ReglasFraude;
import com.alfredodev.semana03.repositorio.CatalogoClientes;
import com.alfredodev.semana03.streams.ReporteTransacciones;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Punto de entrada: corre 10 transacciones contra el motor y muestra, además,
 * la Stream API (total por cliente) y {@code Optional} en acción.
 */
public class DemoMotor {

    public static void main(String[] args) {
        CatalogoClientes catalogo = catalogo();
        List<Transaccion> historial = historial();

        MotorValidacion motor = new MotorValidacion(
                ReglasFraude.montoSobreLimite(),
                ReglasFraude.paisBloqueado(),
                ReglasFraude.horarioInusual(),
                ReglasFraude.velocityCheck(historial));

        System.out.println("===== MOTOR DE VALIDACIÓN (10 transacciones) =====");
        for (Transaccion tx : historial) {
            List<String> motivos = motor.evaluar(tx);
            System.out.printf("%-8s | %-4s | %8.2f | %s%n",
                    tx.cliente(), tx.pais(), tx.monto(),
                    motivos.isEmpty() ? "APROBADA" : "RECHAZADA: " + motivos);
        }

        System.out.println();
        System.out.println("===== STREAM API: total por cliente =====");
        Map<String, Double> totales = ReporteTransacciones.totalPorCliente(historial);
        totales.forEach((cliente, total) ->
                System.out.printf("%-8s -> %.2f%n", cliente, total));

        System.out.println();
        System.out.println("===== OPTIONAL: catálogo de clientes =====");
        System.out.println("buscar(\"María\") -> " + catalogo.buscar("María")
                .map(c -> c.nombre() + (c.esVip() ? " (VIP)" : ""))
                .orElse("No encontrado"));
        System.out.println("buscar(\"Nadie\") -> " + catalogo.buscar("Nadie")
                .map(Cliente::nombre)
                .orElse("No encontrado"));
    }

    private static CatalogoClientes catalogo() {
        return new CatalogoClientes(Map.of(
                "María", new Cliente("María", true),
                "Carlos", new Cliente("Carlos", false),
                "Lucía", new Cliente("Lucía", false),
                "Pedro", new Cliente("Pedro", false)));
    }

    private static List<Transaccion> historial() {
        return List.of(
                new Transaccion(500, "PE", LocalDateTime.of(2026, 9, 15, 10, 30), "María", "web"),
                new Transaccion(12_000, "PE", LocalDateTime.of(2026, 9, 15, 11, 0), "Carlos", "app"),
                new Transaccion(2_000, "PRK", LocalDateTime.of(2026, 9, 15, 11, 5), "Lucía", "web"),
                new Transaccion(3_000, "PE", LocalDateTime.of(2026, 9, 15, 2, 10), "Pedro", "app"),
                new Transaccion(800, "PE", LocalDateTime.of(2026, 9, 15, 10, 30, 15), "María", "web"),
                new Transaccion(900, "PE", LocalDateTime.of(2026, 9, 15, 10, 30, 30), "María", "app"),
                new Transaccion(950, "PE", LocalDateTime.of(2026, 9, 15, 10, 30, 45), "María", "web"),
                new Transaccion(7_000, "PE", LocalDateTime.of(2026, 9, 15, 13, 45), "Carlos", "web"),
                new Transaccion(1_500, "SYR", LocalDateTime.of(2026, 9, 15, 16, 20), "Lucía", "app"),
                new Transaccion(2_200, "PE", LocalDateTime.of(2026, 9, 15, 18, 5), "Pedro", "web"));
    }
}
