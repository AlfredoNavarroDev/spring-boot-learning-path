package com.alfredodev.miniproyectos8.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos8.domain.Usuario;
import com.alfredodev.miniproyectos8.dto.LoginRequest;
import com.alfredodev.miniproyectos8.dto.RegistroRequest;
import com.alfredodev.miniproyectos8.dto.TokenResponse;
import com.alfredodev.miniproyectos8.dto.UsuarioResponse;
import com.alfredodev.miniproyectos8.exception.EmailDuplicadoException;
import com.alfredodev.miniproyectos8.repository.UsuarioRepository;
import com.alfredodev.miniproyectos8.security.JwtService;

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
        // TODO Paso 2:
        // 1. si usuarioRepository.existsByEmail(request.email()) -> EmailDuplicadoException(request.email())
        // 2. armar Usuario con Usuario.builder().email(...).passwordHash(passwordEncoder.encode(request.password()))
        //    .rol(request.rol()).build()
        // 3. guardar con usuarioRepository.save(...) y mapear con UsuarioResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public TokenResponse login(LoginRequest request) {
        // TODO Paso 2:
        // 1. authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        //        request.email(), request.password())) - dispara UsuarioDetailsService + PasswordEncoder
        // 2. buscar el Usuario por email (usuarioRepository.findByEmail(...))
        // 3. devolver TokenResponse.bearer(jwtService.generarAccessToken(usuario.getEmail(),
        //        usuario.getRol().name())) - el rol autenticado queda incrustado en el JWT
        throw new UnsupportedOperationException("TODO");
    }
}
