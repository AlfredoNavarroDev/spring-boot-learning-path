package com.alfredodev.miniproyectos5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos5.domain.Categoria;

// (ya completo — Spring Data implementa la interfaz)
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
