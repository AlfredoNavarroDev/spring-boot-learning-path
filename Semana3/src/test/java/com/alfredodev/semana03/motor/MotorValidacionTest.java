package com.alfredodev.semana03.motor;

import com.alfredodev.semana03.modelo.Transaccion;
import com.alfredodev.semana03.reglas.ReglasFraude;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MotorValidacionTest {

    private final Transaccion limpia =
            new Transaccion(500, "PE", LocalDateTime.of(2026, 9, 15, 12, 0), "Ana", "web");

    @Test
    void transaccionLimpiaDevuelveListaVaciaYEsAprobada() {
        MotorValidacion motor = new MotorValidacion(
                ReglasFraude.montoSobreLimite(),
                ReglasFraude.paisBloqueado(),
                ReglasFraude.horarioInusual(),
                ReglasFraude.velocityCheck(List.of(limpia)));

        assertTrue(motor.evaluar(limpia).isEmpty());
        assertTrue(motor.esAprobada(limpia));
    }

    @Test
    void transaccionQueDisparaTresReglasDevuelveTresMotivos() {
        Transaccion fraudulenta =
                new Transaccion(50_000, "PRK", LocalDateTime.of(2026, 9, 15, 3, 0), "Ana", "web");
        MotorValidacion motor = new MotorValidacion(
                ReglasFraude.montoSobreLimite(),
                ReglasFraude.paisBloqueado(),
                ReglasFraude.horarioInusual(),
                ReglasFraude.velocityCheck(List.of(fraudulenta)));

        List<String> motivos = motor.evaluar(fraudulenta);

        assertEquals(3, motivos.size());
        assertTrue(motivos.contains("MONTO_SOBRE_LIMITE"));
        assertTrue(motivos.contains("PAIS_BLOQUEADO"));
        assertTrue(motivos.contains("HORARIO_INUSUAL"));
        assertFalse(motor.esAprobada(fraudulenta));
    }
}
