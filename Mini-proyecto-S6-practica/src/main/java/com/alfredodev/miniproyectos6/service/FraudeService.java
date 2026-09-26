package com.alfredodev.miniproyectos6.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfredodev.miniproyectos6.dto.EvaluacionFraudeResponse;
import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;
import com.alfredodev.miniproyectos6.dto.TransaccionRequest;
import com.alfredodev.miniproyectos6.modelo.Transaccion;
import com.alfredodev.miniproyectos6.motor.MotorValidacion;
import com.alfredodev.miniproyectos6.reglas.ReglasFraude;

/**
 * Conecta el motor de reglas de la Semana 3 (funciones componibles, en memoria y
 * sin dependencias externas) con el buro de credito externo resiliente de esta
 * semana, para llegar a una decision final.
 */
@Service
public class FraudeService {

    private final MotorValidacion motorValidacion = new MotorValidacion(
            List.of(ReglasFraude.montoSobreLimite(), ReglasFraude.paisBloqueado(), ReglasFraude.horarioInusual()));

    private final BuroCreditoService buroCreditoService;

    public FraudeService(BuroCreditoService buroCreditoService) {
        this.buroCreditoService = buroCreditoService;
    }

    public EvaluacionFraudeResponse evaluar(TransaccionRequest request) {
        // TODO (Paso 5):
        // 1. Armá un Transaccion a partir del request (monto, pais, hora, cliente, canal).
        // 2. Corré motorValidacion.evaluar(transaccion) para obtener las reglas disparadas.
        // 3. Consultá buroCreditoService.consultarScore(request.dni()).
        // 4. Decidí con decidir(reglasDisparadas, score) y devolvé
        //    new EvaluacionFraudeResponse(decision, reglasDisparadas, score).
        throw new UnsupportedOperationException("TODO Paso 5: FraudeService.evaluar");
    }

    private String decidir(List<String> reglasDisparadas, ScoreCrediticioResponse score) {
        // TODO (Paso 5): reglas de decisión, en este orden:
        // - Si reglasDisparadas no está vacía -> "RECHAZADA".
        // - Si score.nivelRiesgo() es "ALTO" -> "REVISION_MANUAL" (esto también cubre
        //   el caso del fallback: si el buro no respondio, se trata como riesgo alto).
        // - Si no -> "APROBADA".
        throw new UnsupportedOperationException("TODO Paso 5: FraudeService.decidir");
    }
}
