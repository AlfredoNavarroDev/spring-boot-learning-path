package org.example;

public record Producto(String id, String nombre, double precio, int stock) {

    public Producto {
        if (stock < 0) {
            throw new StockNegativoException(stock);
        }
    }
}
