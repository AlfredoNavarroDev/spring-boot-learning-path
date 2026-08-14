package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventario {

    private final List<Producto> productos = new ArrayList<>();

    public void agregar(Producto producto) {
        boolean yaExiste = productos.stream()
                .anyMatch(p -> p.id().equals(producto.id()));
        if (yaExiste) {
            throw new ProductoDuplicadoException(producto.id());
        }
        productos.add(producto);
    }

    public List<Producto> buscarPorNombre(String texto) {
        String textoNormalizado = texto.toLowerCase();
        return productos.stream()
                .filter(p -> p.nombre().toLowerCase().contains(textoNormalizado))
                .toList();
    }

    public List<Producto> productosConStockBajo(int umbral) {
        return productos.stream()
                .filter(p -> p.stock() < umbral)
                .toList();
    }

    public Map<String, List<Producto>> agruparPorRangoDePrecio() {
        return productos.stream()
                .collect(Collectors.groupingBy(Inventario::rangoDePrecio));
    }

    private static String rangoDePrecio(Producto producto) {
        double precio = producto.precio();
        if (precio < 50) {
            return "0-50";
        } else if (precio < 100) {
            return "50-100";
        } else if (precio < 200) {
            return "100-200";
        } else {
            return "200+";
        }
    }

    public double valorTotalInventario() {
        return productos.stream()
                .mapToDouble(p -> p.precio() * p.stock())
                .sum();
    }
}
