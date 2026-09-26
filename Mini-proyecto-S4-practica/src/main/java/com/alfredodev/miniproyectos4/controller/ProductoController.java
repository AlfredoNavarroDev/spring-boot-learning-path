package com.alfredodev.miniproyectos4.controller;

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

import com.alfredodev.miniproyectos4.dto.PaginaResponse;
import com.alfredodev.miniproyectos4.dto.ProductoRequest;
import com.alfredodev.miniproyectos4.dto.ProductoResponse;
import com.alfredodev.miniproyectos4.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Equivalente al @Controller('productos') de NestJS: solo recibe/valida DTOs y
 * delega en el service. Sin logica de negocio aca.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de inventario en memoria")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Lista productos paginados")
    public ResponseEntity<PaginaResponse<ProductoResponse>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {
        // TODO (Paso 4): delegar en productoService.listar(pagina, tamanio) y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por id")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        // TODO (Paso 4): delegar en productoService.obtener(id) y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }

    @PostMapping
    @Operation(summary = "Crea un producto")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        // TODO (Paso 4): delegar en productoService.crear(request) y devolver 201 CREATED con el body
        // (ResponseEntity.status(HttpStatus.CREATED).body(...))
        throw new UnsupportedOperationException("TODO");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto existente")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        // TODO (Paso 4): delegar en productoService.actualizar(id, request) y envolver en ResponseEntity.ok(...)
        throw new UnsupportedOperationException("TODO");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // TODO (Paso 4): delegar en productoService.eliminar(id) y devolver 204 (ResponseEntity.noContent().build())
        throw new UnsupportedOperationException("TODO");
    }
}
