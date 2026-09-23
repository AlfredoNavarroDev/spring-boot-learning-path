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
        return new RegistroRegla("MONTO_SOBRE_LIMITE", tx -> tx.monto() > LIMITE_DIARIO);
    }

    public static RegistroRegla paisBloqueado() {
        return new RegistroRegla("PAIS_BLOQUEADO", tx -> PAISES_BLOQUEADOS.contains(tx.pais()));
    }

    public static RegistroRegla horarioInusual() {
        return new RegistroRegla("HORARIO_INUSUAL", tx -> {
            LocalTime hora = tx.hora().toLocalTime();
            return !hora.isBefore(INICIO_MADRUGADA) && hora.isBefore(FIN_MADRUGADA);
        });
    }
}
