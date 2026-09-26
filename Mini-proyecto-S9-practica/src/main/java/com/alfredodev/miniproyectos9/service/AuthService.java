package com.alfredodev.miniproyectos9.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos9.domain.Usuario;
import com.alfredodev.miniproyectos9.dto.LoginRequest;
import com.alfredodev.miniproyectos9.dto.RegistroRequest;
import com.alfredodev.miniproyectos9.dto.TokenResponse;
import com.alfredodev.miniproyectos9.dto.UsuarioResponse;
import com.alfredodev.miniproyectos9.exception.EmailDuplicadoException;
import com.alfredodev.miniproyectos9.repository.UsuarioRepository;
import com.alfredodev.miniproyectos9.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest request) {
        // TODO Paso 3:
        // 1. si usuarioRepository.existsByEmail(request.email()) -> EmailDuplicadoException
        // 2. armar Usuario con Usuario.builder().email(...).passwordHash(passwordEncoder.encode(...))
        //    .rol(request.rol()).build() (la password NUNCA se persiste en texto plano)
        // 3. guardar con usuarioRepository.save(...) y mapear con UsuarioResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public TokenResponse login(LoginRequest request) {
        // TODO Paso 3:
        // 1. authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        //    request.email(), request.password())) - dispara UsuarioDetailsService + el
        //    PasswordEncoder por debajo; lanza BadCredentialsException si falla
        // 2. usuarioRepository.findByEmail(request.email()).orElseThrow(...) para recuperar
        //    el usuario ya autenticado
        // 3. TokenResponse.bearer(jwtService.generarAccessToken(usuario.getEmail(),
        //    usuario.getRol().name())) - el rol viaja como claim del JWT
        throw new UnsupportedOperationException("TODO");
    }
}
