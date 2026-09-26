package com.alfredodev.miniproyectos6.reglas;

import java.time.LocalTime;
import java.util.Set;

import com.alfredodev.miniproyectos6.motor.RegistroRegla;

/** Portado de la Semana 3 (sin el velocity check, que necesita historial fuera de alcance aca). */
public final class ReglasFraude {

    public static final double LIMITE_DIARIO = 10_000.0;
    public static final Set<String> PAISES_BLOQUEADOS = Set.of("PRK", "IRN", "SYR", "CUB");
    public static final LocalTime INICIO_MADRUGADA = LocalTime.of(0, 0);
    public static final LocalTime FIN_MADRUGADA = LocalTime.of(5, 0);

    private ReglasFraude() {
    }

    public static RegistroRegla montoSobreLimite() {
        // TODO (Paso 2): condición de negocio -> "monto > LIMITE_DIARIO".
        // Devolvé new RegistroRegla("MONTO_SOBRE_LIMITE", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 2: ReglasFraude.montoSobreLimite");
    }

    public static RegistroRegla paisBloqueado() {
        // TODO (Paso 2): condición de negocio -> "país en lista negra"
        // (PAISES_BLOQUEADOS.contains(tx.pais())).
        // Devolvé new RegistroRegla("PAIS_BLOQUEADO", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 2: ReglasFraude.paisBloqueado");
    }

    public static RegistroRegla horarioInusual() {
        // TODO (Paso 2): condición de negocio -> "madrugada 00:00–05:00", sobre
        // tx.hora().toLocalTime(): !hora.isBefore(INICIO_MADRUGADA) && hora.isBefore(FIN_MADRUGADA).
        // Devolvé new RegistroRegla("HORARIO_INUSUAL", tx -> ...).
        throw new UnsupportedOperationException("TODO Paso 2: ReglasFraude.horarioInusual");
    }
}
