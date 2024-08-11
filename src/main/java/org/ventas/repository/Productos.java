package org.ventas.repository;

import org.ventas.model.Producto;

import java.util.*;

public class Productos {

    private final Set<Producto> productos;

    public Productos() {
        this.productos = new HashSet<>();
    }

    public void agregarProducto(Producto producto){
        this.productos.add(producto);
    }

    public Optional<Producto> buscarProducto(Long codigo){
        return this.productos.stream().filter(x -> x.mismoCodigo(codigo)).findFirst();
    }

    public List<Producto> getProductos(){
        return this.productos.stream().toList();
    }
}
