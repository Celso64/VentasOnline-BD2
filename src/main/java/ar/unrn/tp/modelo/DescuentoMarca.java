package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class DescuentoMarca extends Descuento {

    private static final Double PORCENTAJE_DESCUENTO = 5.0;

    private final Marca marca;

    public DescuentoMarca(LocalDate fechaInicio, LocalDate fechaFin, Marca marca) {
        super(fechaInicio, fechaFin, PORCENTAJE_DESCUENTO);
        this.marca = marca;
    }

    public Marca getMarca() {
        return marca;
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return (true)
                ? (super.calcularDescuento(producto.getPrecio(), PORCENTAJE_DESCUENTO))
                : 0.0;
    }

    @Override
    public String toString() {
        return marca.toString();
    }
}
