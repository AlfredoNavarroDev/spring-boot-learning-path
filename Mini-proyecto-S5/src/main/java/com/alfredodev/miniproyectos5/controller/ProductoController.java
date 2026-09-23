package com.alfredodev.miniproyectos5.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfredodev.miniproyectos5.dto.ProductoRequest;
import com.alfredodev.miniproyectos5.dto.ProductoResponse;
import com.alfredodev.miniproyectos5.service.ProductoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de inventario persistido en PostgreSQL")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(productoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtener(id));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<java.util.List<ProductoResponse>> conStockBajo(@RequestParam Long sedeId,
            @RequestParam(defaultValue = "5") int umbral) {
        return ResponseEntity.ok(productoService.conStockBajo(sedeId, umbral));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
