package com.alfredodev.semana02practica.component;

import com.alfredodev.semana02practica.entity.Producto;
import org.springframework.stereotype.Component;

// @Component es el estereotipo genérico: es un bean manejado por Spring,
// pero NO es ni @Service (negocio) ni @Repository (datos).
//
// Acá vive lógica de validación reusable, extraída a su propia clase
// (Single Responsibility): InventarioService no necesita saber CÓMO se
// valida el stock, solo si está bajo o no.
@Component
public class StockValidator {

    // TODO (Paso 4): estaBajo(Producto, int umbral)
    //   -> producto != null && producto.stock() <= umbral
    public boolean estaBajo(Producto producto, int umbral) {
        return producto.stock() <= umbral;
    }

    // TODO (Paso 4): noExiste(Producto) -> producto == null
    public boolean noExiste(Producto producto) {
        return producto == null;
    }
}
