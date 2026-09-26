package com.alfredodev.miniproyectos9.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfredodev.miniproyectos9.dto.AjusteStockRequest;
import com.alfredodev.miniproyectos9.dto.ProductoRequest;
import com.alfredodev.miniproyectos9.dto.ProductoResponse;
import com.alfredodev.miniproyectos9.service.ProductoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de inventario con JWT + RBAC (5 roles)")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> listar(Pageable pageable) {
        // TODO Paso 4: return ResponseEntity.ok(productoService.listar(pageable));
        throw new UnsupportedOperationException("TODO");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        // TODO Paso 4: return ResponseEntity.ok(productoService.obtener(id));
        throw new UnsupportedOperationException("TODO");
    }

    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'ABASTECEDOR')")
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        // TODO Paso 4: return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request));
        throw new UnsupportedOperationException("TODO");
    }

    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'TECNICO', 'ABASTECEDOR')")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponse> ajustarStock(@PathVariable Long id,
            @Valid @RequestBody AjusteStockRequest request) {
        // TODO Paso 4: return ResponseEntity.ok(productoService.ajustarStock(id, request.stock()));
        throw new UnsupportedOperationException("TODO");
    }

    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // TODO Paso 4: productoService.eliminar(id); return ResponseEntity.noContent().build();
        throw new UnsupportedOperationException("TODO");
    }
}
