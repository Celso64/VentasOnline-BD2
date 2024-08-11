package org.ventas.model;

import java.time.LocalDate;
import java.util.Collection;

public class DescuentoMarca extends Descuento {

    private static final Integer PORCENTAJE_DESCUENTO = 5;

    private final Marca marca;

    protected DescuentoMarca(LocalDate fechaInicio, LocalDate fechaFin, Marca marca) {
        super(fechaInicio, fechaFin, PORCENTAJE_DESCUENTO);
        this.marca = marca;
    }

    public Marca getMarca() {
        return marca;
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return (producto.esMarca(this.marca))
                ? (super.calcularDescuento(producto.getPrecio(), PORCENTAJE_DESCUENTO))
                : 0.0;
    }
}
