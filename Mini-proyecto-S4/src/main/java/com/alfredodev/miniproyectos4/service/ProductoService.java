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
        List<Producto> todos = productoRepository.findAll();
        int desde = Math.min(pagina * tamanio, todos.size());
        int hasta = Math.min(desde + tamanio, todos.size());
        List<ProductoResponse> contenido = todos.subList(desde, hasta).stream().map(ProductoResponse::desde).toList();
        return new PaginaResponse<>(contenido, pagina, tamanio, todos.size());
    }

    public ProductoResponse obtener(Long id) {
        return ProductoResponse.desde(buscarOFallar(id));
    }

    public ProductoResponse crear(ProductoRequest request) {
        if (productoRepository.existsByNombre(request.nombre())) {
            throw new ProductoDuplicadoException(request.nombre());
        }
        Producto producto = Producto.builder().nombre(request.nombre()).categoria(request.categoria())
                .precio(request.precio()).stock(request.stock()).build();
        return ProductoResponse.desde(productoRepository.save(producto));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto existente = buscarOFallar(id);
        existente.setNombre(request.nombre());
        existente.setCategoria(request.categoria());
        existente.setPrecio(request.precio());
        existente.setStock(request.stock());
        return ProductoResponse.desde(productoRepository.save(existente));
    }

    public void eliminar(Long id) {
        buscarOFallar(id);
        productoRepository.deleteById(id);
    }

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }
}
