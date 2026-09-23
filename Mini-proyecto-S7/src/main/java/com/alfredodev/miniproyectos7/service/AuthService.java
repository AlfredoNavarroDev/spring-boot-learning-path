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
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailDuplicadoException(request.email());
        }

        Usuario usuario = Usuario.builder().email(request.email())
                .passwordHash(passwordEncoder.encode(request.password())).rol(ROL_POR_DEFECTO).build();
        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    public TokenResponse login(LoginRequest request) {
        // Delega la verificacion de password al DaoAuthenticationProvider (que ya usa el
        // PasswordEncoder); si las credenciales son invalidas lanza BadCredentialsException,
        // capturada por el GlobalExceptionHandler.
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado pero no encontrado"));
        return TokenResponse.bearer(jwtService.generarAccessToken(usuario.getEmail(), usuario.getRol()));
    }
}
