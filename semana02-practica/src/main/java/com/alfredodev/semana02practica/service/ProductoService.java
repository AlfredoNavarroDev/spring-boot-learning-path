package com.alfredodev.semana02practica.service;

import com.alfredodev.semana02practica.entity.Producto;
import com.alfredodev.semana02practica.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service = capa de lógica de negocio.
// Delega el acceso a datos al @Repository — no tiene datos propios (SRP).
// En NestJS es lo mismo que un Provider que inyecta un Repository de TypeORM.
@Service
public class ProductoService {

    // TODO (Paso 3): inyectá ProductoRepository por CONSTRUCTOR.
    //   Campo `private final` + constructor explícito (sin @Autowired).
    //   Es la única forma de inyección correcta en producción.
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // TODO (Paso 3): buscarPorNombre(String) -> delega al repository.
    public Producto buscarPorNombre(String nombre) {
        return this.productoRepository.buscarPorNombre(nombre);
    }

    // TODO (Paso 3): listarTodos() -> delega al repository.
    public List<Producto> listarTodos() {
        return this.productoRepository.listarTodos();
    }
}
