package com.alfredodev.semana02practica.service;

import com.alfredodev.semana02practica.component.StockValidator;
import com.alfredodev.semana02practica.dto.ProductoStockDto;
import com.alfredodev.semana02practica.entity.Producto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Orquesta la revisión de stock: busca producto y dispara alerta si está bajo.
// Es el bean que demuestra DI en serio: inyecta un @Service (ProductoService),
// otro @Service (NotificacionService), un @Component (StockValidator) y un
// @Value desde application.properties (Paso 7).
@Service
public class InventarioService {

    // TODO (Pasos 4 y 7): cableá por constructor las 4 dependencias:
    //   - ProductoService productoService
    //   - NotificacionService notificacionService
    //   - StockValidator stockValidator
    //   - int umbralPorDefecto  (con @Value("${inventario.umbral-stock:5}"))
    //   Todos como `private final` + constructor explícito.
    private final ProductoService productoService;
    private final NotificacionService notificacionService;
    private final StockValidator stockValidator;
    private final int umbralPorDefecto;

    public InventarioService(
            ProductoService productoService,
            NotificacionService notificacionService,
            StockValidator stockValidator,
            @Value("${inventario.umbral-stock:5}") int umbralPorDefecto) {
        this.productoService = productoService;
        this.notificacionService = notificacionService;
        this.stockValidator = stockValidator;
        this.umbralPorDefecto = umbralPorDefecto;
    }

    // Sobrecarga: usa el umbral por defecto definido en application.properties.
    public ProductoStockDto revisarStock(String nombreProducto) {
        // TODO: delegá en revisarStock(nombreProducto, umbralPorDefecto)
        return revisarStock(nombreProducto, umbralPorDefecto);
    }

    // Devuelve un DTO (no la entity cruda): el consumidor ve el estado calculado.
    public ProductoStockDto revisarStock(String nombreProducto, int umbral) {
        // TODO (Pasos 4-5):
        //   1. Producto p = productoService.buscarPorNombre(nombreProducto)
        //   2. si stockValidator.noExiste(p) -> alerta + new ProductoStockDto(nombre, 0, "NO ENCONTRADO")
        //   3. si stockValidator.estaBajo(p, umbral) -> alerta + new ProductoStockDto(p.nombre(), p.stock(), "ALERTA")
        //   4. sino -> new ProductoStockDto(p.nombre(), p.stock(), "OK")
        Producto p = productoService.buscarPorNombre(nombreProducto);
        if (stockValidator.noExiste(p)) {
            notificacionService.enviarAlerta("Producto " + nombreProducto + " no encontrado.");
            return  new ProductoStockDto(nombreProducto, 0, "NO ENCONTRADO");
        }

        if (stockValidator.estaBajo(p, umbral)) {
            notificacionService.enviarAlerta("Producto " + nombreProducto + " esta bajo.");
            return new ProductoStockDto(nombreProducto, p.stock(), "ALERTA");
        }
        return new ProductoStockDto(p.nombre(), p.stock(), "OK");
    }
}
