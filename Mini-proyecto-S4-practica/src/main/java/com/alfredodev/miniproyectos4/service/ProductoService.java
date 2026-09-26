package com.alfredodev.miniproyectos4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfredodev.miniproyectos4.domain.Producto;
import com.alfredodev.miniproyectos4.dto.PaginaResponse;
import com.alfredodev.miniproyectos4.dto.ProductoRequest;
import com.alfredodev.miniproyectos4.dto.ProductoResponse;
import com.alfredodev.miniproyectos4.exception.ProductoDuplicadoException;
import com.alfredodev.miniproyectos4.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos4.repository.ProductoRepository;

/**
 * Logica de negocio del CRUD. El controller solo orquesta HTTP; toda regla vive aca
 * (equivalente al *.service.ts de un CRUD en NestJS).
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public PaginaResponse<ProductoResponse> listar(int pagina, int tamanio) {
        // TODO (Paso 3): listado paginado en memoria.
        // 1. traer todos con productoRepository.findAll()
        // 2. calcular "desde"/"hasta" a partir de pagina*tamanio, sin pasarse del size() de la lista
        // 3. mapear el subList a ProductoResponse::desde
        // 4. devolver un new PaginaResponse<>(contenido, pagina, tamanio, todos.size())
        throw new UnsupportedOperationException("TODO");
    }

    public ProductoResponse obtener(Long id) {
        // TODO (Paso 3): buscar por id con buscarOFallar(id) y mapear a ProductoResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public ProductoResponse crear(ProductoRequest request) {
        // TODO (Paso 3):
        // 1. chequeo de duplicado: si productoRepository.existsByNombre(request.nombre()) es true,
        //    lanzar new ProductoDuplicadoException(request.nombre())
        // 2. armar un Producto con Producto.builder()... a partir del request
        // 3. guardarlo con productoRepository.save(...) y devolver ProductoResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        // TODO (Paso 3): buscar el existente con buscarOFallar(id), pisar sus campos con los del request
        // (setNombre/setCategoria/setPrecio/setStock), guardar con productoRepository.save(...)
        // y devolver ProductoResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    public void eliminar(Long id) {
        // TODO (Paso 3): validar que exista con buscarOFallar(id) (para tirar 404 si no) y despues
        // borrar con productoRepository.deleteById(id)
        throw new UnsupportedOperationException("TODO");
    }

    private Producto buscarOFallar(Long id) {
        // TODO (Paso 3): buscar por id con productoRepository.findById(id) y si no existe,
        // orElseThrow(() -> new ProductoNoEncontradoException(id))
        throw new UnsupportedOperationException("TODO");
    }
}
