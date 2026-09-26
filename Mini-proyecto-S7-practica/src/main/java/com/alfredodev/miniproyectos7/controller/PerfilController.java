package com.alfredodev.miniproyectos7.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint protegido de ejemplo: solo responde si el JWT del header es valido. */
@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @GetMapping
    public ResponseEntity<String> miPerfil(Principal principal) {
        // TODO (Paso 6): devolver 200 OK con "Autenticado como: " + principal.getName()
        // (principal ya viene poblado por el SecurityContext que arma el JwtAuthenticationFilter)
        throw new UnsupportedOperationException("TODO");
    }
}
