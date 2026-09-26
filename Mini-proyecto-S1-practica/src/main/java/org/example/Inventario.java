package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventario {

    private final List<Producto> productos = new ArrayList<>();

    public void agregar(Producto producto) {
        // TODO (Paso 3): rechaza duplicados por id con anyMatch (p.id().equals(producto.id())) y lanza ProductoDuplicadoException(producto.id()); si no existe, agregalo a productos
        throw new UnsupportedOperationException("TODO: implementar " + "agregar");
    }

    public List<Producto> buscarPorNombre(String texto) {
        // TODO (Paso 3): filtrá por p.nombre().toLowerCase().contains(texto.toLowerCase())
        throw new UnsupportedOperationException("TODO: implementar " + "buscarPorNombre");
    }

    public List<Producto> productosConStockBajo(int umbral) {
        // TODO (Paso 3): filtrá por p.stock() < umbral
        throw new UnsupportedOperationException("TODO: implementar " + "productosConStockBajo");
    }

    public Map<String, List<Producto>> agruparPorRangoDePrecio() {
        // TODO (Paso 3): Collectors.groupingBy(Inventario::rangoDePrecio) sobre productos.stream()
        throw new UnsupportedOperationException("TODO: implementar " + "agruparPorRangoDePrecio");
    }

    private static String rangoDePrecio(Producto producto) {
        // TODO (Paso 3): clasificá producto.precio() en 4 rangos: "0-50" (<50), "50-100" (<100), "100-200" (<200), "200+" (resto)
        throw new UnsupportedOperationException("TODO: implementar " + "rangoDePrecio");
    }

    public double valorTotalInventario() {
        // TODO (Paso 3): mapToDouble(p -> p.precio() * p.stock()) + sum() sobre productos.stream()
        throw new UnsupportedOperationException("TODO: implementar " + "valorTotalInventario");
    }
}
