package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        inventario.agregar(new Producto("P1", "Mouse inalambrico", 19.99, 20));
        inventario.agregar(new Producto("P2", "Teclado mecanico", 49.99, 2));
        inventario.agregar(new Producto("P3", "Monitor 24 pulgadas", 249.99, 3));
        inventario.agregar(new Producto("P4", "Silla ergonomica", 189.50, 8));

        System.out.println("== Busqueda por nombre (\"teclado\") ==");
        List<Producto> encontrados = inventario.buscarPorNombre("teclado");
        encontrados.forEach(System.out::println);

        System.out.println("\n== Valor total del inventario ==");
        System.out.println(inventario.valorTotalInventario());

        System.out.println("\n== Productos con stock bajo (umbral = 5) ==");
        List<Producto> stockBajo = inventario.productosConStockBajo(5);
        stockBajo.forEach(System.out::println);

        System.out.println("\n== Productos agrupados por rango de precio ==");
        Map<String, List<Producto>> porRango = inventario.agruparPorRangoDePrecio();
        porRango.forEach((rango, productos) -> System.out.println(rango + " -> " + productos));

        System.out.println("\n== Intentando agregar un producto con id duplicado ==");
        try {
            inventario.agregar(new Producto("P1", "Mouse alambrico", 9.99, 15));
        } catch (ProductoDuplicadoException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n== Intentando crear un producto con stock negativo ==");
        try {
            new Producto("P5", "Auriculares", 29.99, -1);
        } catch (StockNegativoException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
