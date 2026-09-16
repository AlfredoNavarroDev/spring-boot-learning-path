package com.alfredodev.semana02practica.repository;

import com.alfredodev.semana02practica.entity.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository = @Component + semántica de "capa de datos".
// Técnicamente es lo mismo que @Component/@Service, pero además:
//   - Spring traduce excepciones de BD a DataAccessException.
//   - Le dice a quien lee: "acá se accede a datos".
// En Semana 4 esto se convierte en un JpaRepository real.
@Repository
public class ProductoRepository {

    // TODO (Paso 2): lista en memoria con 5 productos:
    //   Laptop HP (stock 3, 3200.00), Monitor Dell (12, 890.00),
    //   Teclado Mecánico (2, 250.00), Mouse Inalámbrico (0, 45.00),
    //   Hub USB-C (7, 120.00)
    private final List<Producto> productos = List.of(
            new Producto("Laptop HP", 3, 3200),
            new Producto("Monitor Dell", 12, 890),
            new Producto("Teclado Mecánico", 2, 250),
            new Producto("Mouse Inalámbrico", 0, 45),
            new Producto("Hub USB-C", 7, 120)
    );

    // TODO (Paso 2): buscarPorNombre(String) -> Producto
    //   filtrá con stream + equalsIgnoreCase; devolvé el primero o null si no está.
    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.nombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    // TODO (Paso 2): listarTodos() -> List<Producto>
    public List<Producto> listarTodos() {
        return this.productos;
    }
}
