package org.ventas.repository;

import org.ventas.model.Carrito;
import org.ventas.model.Cliente;
import org.ventas.model.Descuento;
import org.ventas.model.Producto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Carritos {

    private final Map<Cliente, Carrito> carritos;

    public Carritos() {
        this.carritos = new HashMap<>();
    }

    public void agregarRegistro(Cliente cliente, Producto producto){
        if (this.carritos.containsKey(cliente))throw new IllegalStateException("El cliente ya tiene carrito");

        var nuevoCarrito = new Carrito(cliente);
        nuevoCarrito.agregarProducto(producto);
        this.carritos.put(cliente, nuevoCarrito);
    }

    public Carrito getCarrito(Cliente cliente){
        var res = this.carritos.get(cliente);
        if (Objects.isNull(res)) throw new IllegalStateException("El cliente no tiene carrito");
        return res;
    }

    public Carrito getCarrito(Long idCliente){
        var cliente = this.carritos.keySet().stream().filter(x -> x.equals(new Cliente(idCliente))).findFirst();

        if (cliente.isEmpty()) throw new IllegalStateException("El cliente no tiene carrito");

        return carritos.get(cliente.get());
    }

    public Double liquidarCarrito(Set<Descuento> descuentos){
        return 0.0;
    }
}
