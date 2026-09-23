package com.alfredodev.miniproyectos5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos5.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
