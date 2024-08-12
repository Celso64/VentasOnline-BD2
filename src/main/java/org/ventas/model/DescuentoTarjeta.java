package org.ventas.model;

import java.time.LocalDate;
import java.util.Collection;

public class DescuentoTarjeta extends Descuento {

    private static final Integer PORCENTAJE_DESCUENTO = 8;

    private final Tarjeta tarjeta;

    public DescuentoTarjeta(LocalDate fechaInicio, LocalDate fechaFin, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin, PORCENTAJE_DESCUENTO);
        this.tarjeta = tarjeta;
    }

    public MarcaTarjeta getMarca(){
        return this.tarjeta.getMarca();
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return super.calcularDescuento(producto.getPrecio(), PORCENTAJE_DESCUENTO);
    }
}
