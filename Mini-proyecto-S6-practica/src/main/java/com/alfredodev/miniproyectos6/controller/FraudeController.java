package com.alfredodev.miniproyectos6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfredodev.miniproyectos6.dto.EvaluacionFraudeResponse;
import com.alfredodev.miniproyectos6.dto.ScoreCrediticioResponse;
import com.alfredodev.miniproyectos6.dto.TransaccionRequest;
import com.alfredodev.miniproyectos6.service.BuroCreditoService;
import com.alfredodev.miniproyectos6.service.FraudeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class FraudeController {

    private final FraudeService fraudeService;
    private final BuroCreditoService buroCreditoService;

    public FraudeController(FraudeService fraudeService, BuroCreditoService buroCreditoService) {
        this.fraudeService = fraudeService;
        this.buroCreditoService = buroCreditoService;
    }

    @PostMapping("/fraude/evaluar")
    public ResponseEntity<EvaluacionFraudeResponse> evaluar(@Valid @RequestBody TransaccionRequest request) {
        // TODO (Paso 6): delegá en fraudeService.evaluar(request) y devolvé
        // ResponseEntity.ok(...). Sin lógica de negocio acá.
        throw new UnsupportedOperationException("TODO Paso 6: FraudeController.evaluar");
    }

    @GetMapping("/buro-credito/{dni}")
    public ResponseEntity<ScoreCrediticioResponse> consultarScore(@PathVariable String dni) {
        // TODO (Paso 6): delegá en buroCreditoService.consultarScore(dni) y devolvé
        // ResponseEntity.ok(...).
        throw new UnsupportedOperationException("TODO Paso 6: FraudeController.consultarScore");
    }
}
