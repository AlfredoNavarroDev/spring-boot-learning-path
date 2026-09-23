package com.alfredodev.miniproyectos8.controller;

import java.util.List;

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

import com.alfredodev.miniproyectos8.dto.AjusteStockRequest;
import com.alfredodev.miniproyectos8.dto.ProductoRequest;
import com.alfredodev.miniproyectos8.dto.ProductoResponse;
import com.alfredodev.miniproyectos8.service.ProductoService;

import jakarta.validation.Valid;

/**
 * RBAC por metodo con @PreAuthorize (equivalente a @Roles(...) + RolesGuard en Nest):
 * cada regla vive junto al endpoint que protege, no en una tabla central que hay que
 * cruzar con el codigo para saber que hace.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Cualquier rol autenticado puede consultar el inventario.
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    // Dar de alta un producto nuevo es una decision de compras/administracion.
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'ABASTECEDOR')")
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request));
    }

    // Ajustar stock es tarea operativa: tecnico y abastecedor la hacen a diario,
    // vendedor no (solo consulta, no toca inventario).
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'TECNICO', 'ABASTECEDOR')")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponse> ajustarStock(@PathVariable Long id,
            @Valid @RequestBody AjusteStockRequest request) {
        return ResponseEntity.ok(productoService.ajustarStock(id, request.stock()));
    }

    // Eliminar un producto es una decision de alto impacto: solo los dos roles de mayor jerarquia.
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
