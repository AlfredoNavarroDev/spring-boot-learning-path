package com.alfredodev.miniproyectos8.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos8.domain.Producto;
import com.alfredodev.miniproyectos8.dto.ProductoRequest;
import com.alfredodev.miniproyectos8.dto.ProductoResponse;
import com.alfredodev.miniproyectos8.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos8.repository.ProductoRepository;

/**
 * Sin logica de autorizacion aca a proposito: quien decide "puede este rol llamar a
 * este metodo" es @PreAuthorize en el controller (ver ProductoController), no el
 * service. El service no sabe que existen roles.
 */
@Service
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream().map(ProductoResponse::desde).toList();
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = Producto.builder().nombre(request.nombre()).stock(request.stock()).build();
        return ProductoResponse.desde(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponse ajustarStock(Long id, int nuevoStock) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
        producto.setStock(nuevoStock);
        return ProductoResponse.desde(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }
        productoRepository.deleteById(id);
    }
}
