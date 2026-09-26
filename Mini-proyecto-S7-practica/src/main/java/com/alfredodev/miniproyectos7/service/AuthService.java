package com.alfredodev.miniproyectos7.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos7.domain.Usuario;
import com.alfredodev.miniproyectos7.dto.LoginRequest;
import com.alfredodev.miniproyectos7.dto.RegistroRequest;
import com.alfredodev.miniproyectos7.dto.TokenResponse;
import com.alfredodev.miniproyectos7.dto.UsuarioResponse;
import com.alfredodev.miniproyectos7.exception.EmailDuplicadoException;
import com.alfredodev.miniproyectos7.repository.UsuarioRepository;
import com.alfredodev.miniproyectos7.security.JwtService;

@Service
public class AuthService {

    private static final String ROL_POR_DEFECTO = "CLIENTE";

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
        // TODO (Paso 5):
        // 1. si usuarioRepository.existsByEmail(request.email()) es true, lanzar
        //    new EmailDuplicadoException(request.email())
        // 2. armar un Usuario con Usuario.builder().email(request.email())
        //    .passwordHash(passwordEncoder.encode(request.password())) - NUNCA guardar el password en texto plano
        //    .rol(ROL_POR_DEFECTO).build()
        // 3. guardar con usuarioRepository.save(...) y devolver UsuarioResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public TokenResponse login(LoginRequest request) {
        // TODO (Paso 5):
        // 1. delegar la verificacion de password a
        //    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),
        //    request.password())) - NUNCA comparar hashes a mano; si las credenciales son invalidas esto
        //    lanza BadCredentialsException, capturada por el GlobalExceptionHandler
        // 2. buscar el usuario con usuarioRepository.findByEmail(request.email())
        //    .orElseThrow(() -> new IllegalStateException("Usuario autenticado pero no encontrado"))
        // 3. devolver TokenResponse.bearer(jwtService.generarAccessToken(usuario.getEmail(), usuario.getRol()))
        throw new UnsupportedOperationException("TODO");
    }
}
