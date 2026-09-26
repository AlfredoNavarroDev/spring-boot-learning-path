package com.alfredodev.miniproyectos8.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfredodev.miniproyectos8.dto.LoginRequest;
import com.alfredodev.miniproyectos8.dto.RegistroRequest;
import com.alfredodev.miniproyectos8.dto.TokenResponse;
import com.alfredodev.miniproyectos8.dto.UsuarioResponse;
import com.alfredodev.miniproyectos8.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        // TODO Paso 3: delegar en authService.registrar(request) y devolver 201 CREATED con el body
        throw new UnsupportedOperationException("TODO");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        // TODO Paso 3: delegar en authService.login(request) y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }
}
