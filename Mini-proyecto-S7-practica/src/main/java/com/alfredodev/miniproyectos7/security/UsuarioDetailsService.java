package com.alfredodev.miniproyectos7.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alfredodev.miniproyectos7.domain.Usuario;
import com.alfredodev.miniproyectos7.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // TODO (Paso 3):
        // 1. buscar con usuarioRepository.findByEmail(email)
        //    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email))
        // 2. armar un org.springframework.security.core.userdetails.User con
        //    (usuario.getEmail(), usuario.getPasswordHash(),
        //    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())))
        throw new UnsupportedOperationException("TODO");
    }
}
