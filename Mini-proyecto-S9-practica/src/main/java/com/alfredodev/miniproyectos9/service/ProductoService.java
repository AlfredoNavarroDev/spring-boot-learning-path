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
        // TODO Paso 3: productoRepository.findAll(pageable).map(ProductoResponse::desde)
        throw new UnsupportedOperationException("TODO");
    }

    public ProductoResponse obtener(Long id) {
        // TODO Paso 3: return ProductoResponse.desde(buscarOFallar(id));
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        // TODO Paso 3:
        // 1. buscar Categoria por request.categoriaId(), si no existe -> CategoriaNoEncontradaException
        // 2. buscar Sede por request.sedeId(), si no existe -> SedeNoEncontradaException
        // 3. armar Producto con Producto.builder()...build() usando esa categoria/sede
        // 4. guardar con productoRepository.save(...) y mapear con ProductoResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public ProductoResponse ajustarStock(Long id, int nuevoStock) {
        // TODO Paso 3: buscarOFallar(id), setStock(nuevoStock) sobre la entity managed
        // (no hace falta save explicito: el UPDATE sale con el flush de esta transaccion)
        // y devolver ProductoResponse.desde(producto)
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public void eliminar(Long id) {
        // TODO Paso 3: si no existe (productoRepository.existsById(id)) -> ProductoNoEncontradoException;
        // si existe, productoRepository.deleteById(id)
        throw new UnsupportedOperationException("TODO");
    }

    private Producto buscarOFallar(Long id) {
        // TODO Paso 3: productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id))
        throw new UnsupportedOperationException("TODO");
    }
}
