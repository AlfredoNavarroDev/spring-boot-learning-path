package com.alfredodev.miniproyectos9.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos9.domain.Categoria;
import com.alfredodev.miniproyectos9.domain.Producto;
import com.alfredodev.miniproyectos9.domain.Sede;
import com.alfredodev.miniproyectos9.dto.ProductoRequest;
import com.alfredodev.miniproyectos9.dto.ProductoResponse;
import com.alfredodev.miniproyectos9.exception.CategoriaNoEncontradaException;
import com.alfredodev.miniproyectos9.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos9.exception.SedeNoEncontradaException;
import com.alfredodev.miniproyectos9.repository.CategoriaRepository;
import com.alfredodev.miniproyectos9.repository.ProductoRepository;
import com.alfredodev.miniproyectos9.repository.SedeRepository;

@Service
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SedeRepository sedeRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository,
            SedeRepository sedeRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.sedeRepository = sedeRepository;
    }

    public Page<ProductoResponse> listar(Pageable pageable) {
        return productoRepository.findAll(pageable).map(ProductoResponse::desde);
    }

    public ProductoResponse obtener(Long id) {
        return ProductoResponse.desde(buscarOFallar(id));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoriaNoEncontradaException(request.categoriaId()));
        Sede sede = sedeRepository.findById(request.sedeId())
                .orElseThrow(() -> new SedeNoEncontradaException(request.sedeId()));

        Producto producto = Producto.builder().nombre(request.nombre()).precio(request.precio())
                .stock(request.stock()).categoria(categoria).sede(sede).build();
        return ProductoResponse.desde(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponse ajustarStock(Long id, int nuevoStock) {
        Producto producto = buscarOFallar(id);
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

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }
}
