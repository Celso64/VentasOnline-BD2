package org.ventas.model;

import java.util.HashSet;
import java.util.Set;

public class Carrito {

    private final Set<Producto> productos;

    public Carrito() {
        this.productos = new HashSet<>();
    }

    public void agregarProducto(Producto producto){
        this.productos.add(producto);
    }

    public void quitarProducto(Producto producto){
        this.productos.remove(producto);
    }

    public void quitarProducto(Long idProducto){
        this.productos.removeIf(x -> x.mismoCodigo(idProducto));
    }

    public Double liquidar(){
        return 0.0;
    }
}
