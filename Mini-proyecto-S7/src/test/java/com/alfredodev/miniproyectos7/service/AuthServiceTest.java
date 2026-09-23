package com.alfredodev.miniproyectos7.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alfredodev.miniproyectos7.domain.Usuario;
import com.alfredodev.miniproyectos7.dto.LoginRequest;
import com.alfredodev.miniproyectos7.dto.RegistroRequest;
import com.alfredodev.miniproyectos7.exception.EmailDuplicadoException;
import com.alfredodev.miniproyectos7.repository.UsuarioRepository;
import com.alfredodev.miniproyectos7.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @Test
    void registraUnUsuarioNuevoConPasswordHasheado() {
        authService = new AuthService(usuarioRepository, passwordEncoder, authenticationManager, jwtService);
        when(usuarioRepository.existsByEmail("ana@correo.com")).thenReturn(false);
        when(passwordEncoder.encode("clave12345")).thenReturn("hash-simulado");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });

        var response = authService.registrar(new RegistroRequest("ana@correo.com", "clave12345"));

        assertThat(response.email()).isEqualTo("ana@correo.com");
        assertThat(response.rol()).isEqualTo("CLIENTE");
    }

    @Test
    void rechazaRegistroConEmailYaExistente() {
        authService = new AuthService(usuarioRepository, passwordEncoder, authenticationManager, jwtService);
        when(usuarioRepository.existsByEmail("ana@correo.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.registrar(new RegistroRequest("ana@correo.com", "clave12345")))
                .isInstanceOf(EmailDuplicadoException.class);
    }

    @Test
    void emiteUnTokenAlLoguearseConCredencialesValidas() {
        authService = new AuthService(usuarioRepository, passwordEncoder, authenticationManager, jwtService);
        Usuario usuario = Usuario.builder().id(1L).email("ana@correo.com").passwordHash("hash").rol("CLIENTE")
                .build();
        when(usuarioRepository.findByEmail("ana@correo.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generarAccessToken("ana@correo.com", "CLIENTE")).thenReturn("token-simulado");

        var response = authService.login(new LoginRequest("ana@correo.com", "clave12345"));

        assertThat(response.accessToken()).isEqualTo("token-simulado");
        assertThat(response.tokenType()).isEqualTo("Bearer");
    }
}
