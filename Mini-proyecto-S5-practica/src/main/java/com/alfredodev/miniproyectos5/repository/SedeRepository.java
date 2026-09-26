package com.alfredodev.miniproyectos5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos5.domain.Sede;

// (ya completo — Spring Data implementa la interfaz)
public interface SedeRepository extends JpaRepository<Sede, Long> {
}
