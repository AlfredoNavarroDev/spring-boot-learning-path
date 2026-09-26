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
        // TODO Paso 3: delegar en productoService.listar() y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }

    // Dar de alta un producto nuevo es una decision de compras/administracion.
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'ABASTECEDOR')")
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        // TODO Paso 3: delegar en productoService.crear(request) y devolver 201 CREATED con el body
        throw new UnsupportedOperationException("TODO");
    }

    // Ajustar stock es tarea operativa: tecnico y abastecedor la hacen a diario,
    // vendedor no (solo consulta, no toca inventario).
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR', 'TECNICO', 'ABASTECEDOR')")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponse> ajustarStock(@PathVariable Long id,
            @Valid @RequestBody AjusteStockRequest request) {
        // TODO Paso 3: delegar en productoService.ajustarStock(id, request.stock()) y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }

    // Eliminar un producto es una decision de alto impacto: solo los dos roles de mayor jerarquia.
    @PreAuthorize("hasAnyRole('PROPIETARIO', 'ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // TODO Paso 3: delegar en productoService.eliminar(id) y devolver 204 (ResponseEntity.noContent().build())
        throw new UnsupportedOperationException("TODO");
    }
}
