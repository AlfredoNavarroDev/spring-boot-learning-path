package com.alfredodev.semana03.reglas;

import com.alfredodev.semana03.modelo.Transaccion;
import com.alfredodev.semana03.motor.RegistroRegla;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * Las 4 reglas reales de detección de fraude, cada una como un
 * {@link RegistroRegla} (nombre + {@code Predicate<Transaccion>}).
 */
public final class ReglasFraude {

    public static final double LIMITE_DIARIO = 10_000.0;
    public static final Set<String> PAISES_BLOQUEADOS = Set.of("PRK", "IRN", "SYR", "CUB");
    public static final int MAX_TX_POR_MINUTO = 3;
    public static final LocalTime INICIO_MADRUGADA = LocalTime.of(0, 0);
    public static final LocalTime FIN_MADRUGADA = LocalTime.of(5, 0);

    private ReglasFraude() {
    }

    /** 1. Monto superior al límite diario. */
    public static RegistroRegla montoSobreLimite() {
        return new RegistroRegla("MONTO_SOBRE_LIMITE",
                tx -> tx.monto() > LIMITE_DIARIO);
    }

    /** 2. País de origen en lista negra. */
    public static RegistroRegla paisBloqueado() {
        return new RegistroRegla("PAIS_BLOQUEADO",
                tx -> PAISES_BLOQUEADOS.contains(tx.pais()));
    }

    /** 3. Horario inusual: madrugada (00:00–05:00). */
    public static RegistroRegla horarioInusual() {
        return new RegistroRegla("HORARIO_INUSUAL",
                tx -> {
                    LocalTime hora = tx.hora().toLocalTime();
                    return !hora.isBefore(INICIO_MADRUGADA) && hora.isBefore(FIN_MADRUGADA);
                });
    }

    /**
     * 4. Velocity check: más de {@value #MAX_TX_POR_MINUTO} transacciones del
     * mismo cliente dentro de 1 minuto. Necesita el historial completo, por eso
     * recibe la lista y cierra sobre ella (closure).
     */
    public static RegistroRegla velocityCheck(List<Transaccion> historial) {
        return new RegistroRegla("VELOCIDAD_EXCESIVA",
                tx -> historial.stream()
                        .filter(o -> o.cliente().equals(tx.cliente()))
                        .filter(o -> enVentanaDeUnMinuto(o.hora(), tx.hora()))
                        .count() > MAX_TX_POR_MINUTO);
    }

    private static boolean enVentanaDeUnMinuto(LocalDateTime a, LocalDateTime b) {
        return Math.abs(Duration.between(a, b).toSeconds()) <= 60;
    }
}
