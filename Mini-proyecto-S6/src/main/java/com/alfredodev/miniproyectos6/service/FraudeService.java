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
        Transaccion transaccion = new Transaccion(request.monto(), request.pais(), request.hora(),
                request.cliente(), request.canal());

        List<String> reglasDisparadas = motorValidacion.evaluar(transaccion);
        ScoreCrediticioResponse score = buroCreditoService.consultarScore(request.dni());

        String decision = decidir(reglasDisparadas, score);
        return new EvaluacionFraudeResponse(decision, reglasDisparadas, score);
    }

    private String decidir(List<String> reglasDisparadas, ScoreCrediticioResponse score) {
        if (!reglasDisparadas.isEmpty()) {
            return "RECHAZADA";
        }
        if ("ALTO".equals(score.nivelRiesgo())) {
            // Incluye el caso del fallback: si el buro no respondio, se trata como riesgo alto.
            return "REVISION_MANUAL";
        }
        return "APROBADA";
    }
}
