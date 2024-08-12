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

    public Long agregarCliente(String nombre, String apellido, String email, String DNI){
        var nuevoCliente = new Cliente(nombre, apellido, email,DNI);
        this.clientes.agregarCliente(nuevoCliente);
        return nuevoCliente.getId();
    }

    public Producto agregarProducto(String nombre, String descripcion, String nombreMarca, Double precio){
        var nuevoProducto = new Producto(nombre, descripcion, nombreMarca, precio);
        this.productos.agregarProducto(nuevoProducto);
        return nuevoProducto;
    }

    public Optional<Tarjeta> asociarTarjeta(Long idCliente, Integer numero, Integer codigoSeguridad, String fechaVencimiento, String MarcaTarjeta){
        var nuevaTarjeta = new Tarjeta(numero, codigoSeguridad, fechaVencimiento, new MarcaTarjeta(fechaVencimiento));
        var cliente = clientes.buscarCliente(idCliente);

        if(cliente.isPresent()){
            cliente.get().agregarTarjeta(nuevaTarjeta);
            return Optional.of(nuevaTarjeta);
        }
        return Optional.empty();
    }

}
