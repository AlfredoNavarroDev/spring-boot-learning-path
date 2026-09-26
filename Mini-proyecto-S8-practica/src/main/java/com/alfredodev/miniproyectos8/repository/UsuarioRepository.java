package com.alfredodev.miniproyectos8.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos8.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
