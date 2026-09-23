package com.alfredodev.miniproyectos8.dto;

import com.alfredodev.miniproyectos8.domain.Usuario;

public record UsuarioResponse(Long id, String email, String rol) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), usuario.getRol().name());
    }
}
