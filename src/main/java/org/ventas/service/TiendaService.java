package org.ventas.service;

import org.ventas.model.*;
import org.ventas.repository.*;

import java.util.Optional;

public class TiendaService {

    private final Ventas ventas;
    private final Carritos carritos;
    private final Descuentos descuentos;
    private final Clientes clientes;
    private final Productos productos;

    public TiendaService() {
        this.ventas = new Ventas();
        this.carritos = new Carritos();
        this.descuentos = new Descuentos();
        this.clientes = new Clientes();
        this.productos = new Productos();
    }

    public Long agregarCliente(String nombre, String apellido, String email, String DNI) {
        var nuevoCliente = new Cliente(nombre, apellido, email, DNI);
        this.clientes.agregarCliente(nuevoCliente);
        return nuevoCliente.getId();
    }

    public Long agregarProducto(String nombre, String descripcion, String nombreMarca, Double precio) {
        var nuevoProducto = new Producto(nombre, descripcion, nombreMarca, precio);
        this.productos.agregarProducto(nuevoProducto);
        return nuevoProducto.getCodigo();
    }

    public Optional<Tarjeta> asociarTarjeta(Long idCliente, String numero, Integer codigoSeguridad, String fechaVencimiento, String MarcaTarjeta) {
        var nuevaTarjeta = new Tarjeta(numero, codigoSeguridad, fechaVencimiento, new MarcaTarjeta(fechaVencimiento));
        var cliente = clientes.buscarCliente(idCliente);

        if (cliente.isPresent()) {
            cliente.get().agregarTarjeta(nuevaTarjeta);
            return Optional.of(nuevaTarjeta);
        }
        return Optional.empty();
    }

    public void agregarAlCarrito(Long idCliente, Long idProducto) {
        var cliente = clientes.buscarCliente(idCliente);
        var producto = productos.buscarProducto(idProducto);

        if (cliente.isEmpty()) throw new IllegalArgumentException("El cliente no existe");
        if (producto.isEmpty()) throw new IllegalArgumentException("El producto no existe");

        carritos.agregarRegistro(cliente.get(), producto.get());
    }



}
