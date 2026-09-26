package com.alfredodev.miniproyectos7.dto;

import com.alfredodev.miniproyectos7.domain.Usuario;

// Nunca se expone passwordHash aca, aunque exista en la entity.
public record UsuarioResponse(Long id, String email, String rol) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), usuario.getRol());
    }
}
