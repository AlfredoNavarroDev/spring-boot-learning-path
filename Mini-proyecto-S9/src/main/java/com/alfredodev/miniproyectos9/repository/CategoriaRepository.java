package com.alfredodev.miniproyectos9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos9.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
