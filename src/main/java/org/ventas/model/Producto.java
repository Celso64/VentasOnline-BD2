package org.ventas.model;

import java.util.Objects;

public class Producto {

    private static final IdCounter<Long> idCounter = new LongCounter();

    private final String nombre, descripcion;

    private final Long codigo;

    private final Marca marca;

    private Double precio;

    public Producto(String nombre, String descripcion, String marca, Double precio) {
        this.codigo = idCounter.getId();
        this.nombre = Objects.requireNonNull(nombre);
        this.descripcion = Objects.requireNonNull(descripcion);
        this.marca = new Marca(marca);
        this.precio = Objects.requireNonNull(precio);
    }

    public String getMarca() {
        return marca.toString();
    }

    public Boolean mismoCodigo(Long codigo){
        return this.codigo.equals(codigo);
    }

    public Boolean esMarca(Marca marca){
        return this.marca.equals(marca);
    }

    public void cambiarPrecio(Double nuevoPrecio){
        this.precio = nuevoPrecio;
    }

    public Double getPrecio(){
        return this.precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
