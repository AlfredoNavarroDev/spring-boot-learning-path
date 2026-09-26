package org.example;

public record Producto(String id, String nombre, double precio, int stock) {

    public Producto {
        // TODO (Paso 1): valida que stock >= 0, sino lanza StockNegativoException(stock)
    }
}
