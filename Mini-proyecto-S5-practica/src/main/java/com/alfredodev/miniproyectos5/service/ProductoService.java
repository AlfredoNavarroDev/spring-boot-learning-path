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
        // TODO Paso 4: productoRepository.findAll(pageable).map(ProductoResponse::desde)
        // Recorda: open-in-view=false (ver application.yml) - ProductoResponse.desde() lee
        // categoria/sede (LAZY) y esto tiene que pasar DENTRO de esta transaccion @Transactional,
        // no despues en el controller/vista, o vas a explotar con LazyInitializationException.
        throw new UnsupportedOperationException("TODO");
    }

    public ProductoResponse obtener(Long id) {
        // TODO Paso 4: return ProductoResponse.desde(buscarOFallar(id));
        // Recorda: acceder a categoria/sede del producto tiene que pasar dentro de esta transaccion.
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        // TODO Paso 4:
        // 1. buscar Categoria por request.categoriaId(), si no existe -> CategoriaNoEncontradaException
        // 2. buscar Sede por request.sedeId(), si no existe -> SedeNoEncontradaException
        // 3. armar Producto con Producto.builder()...build() usando esa categoria/sede
        // 4. guardar con productoRepository.save(...) y mapear con ProductoResponse.desde(...)
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        // TODO Paso 4:
        // 1. buscarOFallar(id) para traer la entity managed
        // 2. resolver categoria/sede nuevas (misma logica que crear)
        // 3. setear los campos sobre la entity managed y devolver ProductoResponse.desde(existente)
        //    (no hace falta save explicito: es un managed entity, el UPDATE sale con el flush
        //    de esta transaccion @Transactional)
        throw new UnsupportedOperationException("TODO");
    }

    @Transactional
    public void eliminar(Long id) {
        // TODO Paso 4: si no existe (productoRepository.existsById(id)) -> ProductoNoEncontradoException;
        // si existe, productoRepository.deleteById(id)
        throw new UnsupportedOperationException("TODO");
    }

    public java.util.List<ProductoResponse> conStockBajo(Long sedeId, int umbral) {
        // TODO Paso 4: productoRepository.buscarConStockBajo(sedeId, umbral).stream()
        // .map(ProductoResponse::desde).toList()
        // Recorda: el mapeo a ProductoResponse toca categoria/sede LAZY, tiene que quedar
        // dentro de esta transaccion de solo lectura.
        throw new UnsupportedOperationException("TODO");
    }

    private Producto buscarOFallar(Long id) {
        // TODO Paso 4: productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id))
        throw new UnsupportedOperationException("TODO");
    }
}
