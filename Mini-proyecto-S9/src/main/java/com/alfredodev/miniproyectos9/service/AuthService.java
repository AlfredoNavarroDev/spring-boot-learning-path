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
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailDuplicadoException(request.email());
        }

        Usuario usuario = Usuario.builder().email(request.email())
                .passwordHash(passwordEncoder.encode(request.password())).rol(request.rol()).build();
        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado pero no encontrado"));
        return TokenResponse.bearer(jwtService.generarAccessToken(usuario.getEmail(), usuario.getRol().name()));
    }
}
