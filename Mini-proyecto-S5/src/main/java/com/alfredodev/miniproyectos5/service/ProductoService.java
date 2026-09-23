package com.alfredodev.miniproyectos5.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfredodev.miniproyectos5.domain.Categoria;
import com.alfredodev.miniproyectos5.domain.Producto;
import com.alfredodev.miniproyectos5.domain.Sede;
import com.alfredodev.miniproyectos5.dto.ProductoRequest;
import com.alfredodev.miniproyectos5.dto.ProductoResponse;
import com.alfredodev.miniproyectos5.exception.CategoriaNoEncontradaException;
import com.alfredodev.miniproyectos5.exception.ProductoNoEncontradoException;
import com.alfredodev.miniproyectos5.exception.SedeNoEncontradaException;
import com.alfredodev.miniproyectos5.repository.CategoriaRepository;
import com.alfredodev.miniproyectos5.repository.ProductoRepository;
import com.alfredodev.miniproyectos5.repository.SedeRepository;

@Service
@Transactional(readOnly = true) // por defecto de solo lectura; los metodos de escritura lo sobreescriben
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
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto existente = buscarOFallar(id);
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoriaNoEncontradaException(request.categoriaId()));
        Sede sede = sedeRepository.findById(request.sedeId())
                .orElseThrow(() -> new SedeNoEncontradaException(request.sedeId()));

        existente.setNombre(request.nombre());
        existente.setPrecio(request.precio());
        existente.setStock(request.stock());
        existente.setCategoria(categoria);
        existente.setSede(sede);
        return ProductoResponse.desde(existente); // managed entity: el UPDATE sale con el flush de la transaccion
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }
        productoRepository.deleteById(id);
    }

    public java.util.List<ProductoResponse> conStockBajo(Long sedeId, int umbral) {
        return productoRepository.buscarConStockBajo(sedeId, umbral).stream().map(ProductoResponse::desde).toList();
    }

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }
}
