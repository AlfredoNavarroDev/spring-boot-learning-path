package com.alfredodev.miniproyectos8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfredodev.miniproyectos8.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
