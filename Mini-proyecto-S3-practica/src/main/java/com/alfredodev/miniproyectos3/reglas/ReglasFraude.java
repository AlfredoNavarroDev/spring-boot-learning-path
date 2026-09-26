package com.alfredodev.miniproyectos3.reglas;

import com.alfredodev.miniproyectos3.modelo.Transaccion;
import com.alfredodev.miniproyectos3.motor.RegistroRegla;

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
        // TODO (Paso 5): condición de negocio -> "monto > LIMITE_DIARIO".
        // Devolvé new RegistroRegla("MONTO_SOBRE_LIMITE", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 5: ReglasFraude.montoSobreLimite");
    }

    /** 2. País de origen en lista negra. */
    public static RegistroRegla paisBloqueado() {
        // TODO (Paso 5): condición de negocio -> "país en lista negra"
        // (PAISES_BLOQUEADOS.contains(tx.pais())).
        // Devolvé new RegistroRegla("PAIS_BLOQUEADO", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 5: ReglasFraude.paisBloqueado");
    }

    /** 3. Horario inusual: madrugada (00:00–05:00). */
    public static RegistroRegla horarioInusual() {
        // TODO (Paso 5): condición de negocio -> "madrugada 00:00–05:00"
        // (hora >= INICIO_MADRUGADA && hora < FIN_MADRUGADA, sobre tx.hora().toLocalTime()).
        // Devolvé new RegistroRegla("HORARIO_INUSUAL", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 5: ReglasFraude.horarioInusual");
    }

    /**
     * 4. Velocity check: más de {@value #MAX_TX_POR_MINUTO} transacciones del
     * mismo cliente dentro de 1 minuto. Necesita el historial completo, por eso
     * recibe la lista y cierra sobre ella (closure).
     */
    public static RegistroRegla velocityCheck(List<Transaccion> historial) {
        // TODO (Paso 5): condición de negocio -> "más de MAX_TX_POR_MINUTO (3)
        // transacciones del mismo cliente dentro de 1 minuto". Filtrá `historial`
        // por mismo cliente (o.cliente().equals(tx.cliente())) y por ventana de
        // 1 minuto (enVentanaDeUnMinuto(o.hora(), tx.hora())), contá y comparalo
        // con MAX_TX_POR_MINUTO. Devolvé new RegistroRegla("VELOCIDAD_EXCESIVA", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 5: ReglasFraude.velocityCheck");
    }

    private static boolean enVentanaDeUnMinuto(LocalDateTime a, LocalDateTime b) {
        // TODO (Paso 5): true si el valor absoluto de la diferencia en segundos
        // entre `a` y `b` (Duration.between(a, b).toSeconds()) es <= 60.
        throw new UnsupportedOperationException("TODO Paso 5: ReglasFraude.enVentanaDeUnMinuto");
    }
}
