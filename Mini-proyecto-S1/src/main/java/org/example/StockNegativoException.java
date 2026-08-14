package org.example;

public class StockNegativoException extends RuntimeException {

    public StockNegativoException(int stock) {
        super("El stock no puede ser negativo: " + stock);
    }
}
