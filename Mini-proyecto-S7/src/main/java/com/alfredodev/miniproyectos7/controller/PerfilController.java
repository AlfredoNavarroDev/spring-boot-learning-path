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
        return ResponseEntity.ok("Autenticado como: " + principal.getName());
    }
}
