package org.ventas.model;

import org.ventas.model.util.Porcentaje;
import org.ventas.model.util.RangoFechas;

import java.time.LocalDate;
import java.util.Collection;

public abstract class Descuento {

    private final LocalDate fechaInicio, fechaFin;
    private Double porcentajeDescuento;

    protected Descuento(LocalDate fechaInicio, LocalDate fechaFin, Integer porcentajeDescuento) {
        var rango = new RangoFechas(fechaInicio, fechaFin);
        this.fechaInicio = rango.getInicio();
        this.fechaFin = rango.getFin();
        this.porcentajeDescuento = new Porcentaje(porcentajeDescuento).getCoeficiente();
    }

    protected Double getCoeficiente() {
        return this.porcentajeDescuento;
    }

    public Boolean estaVigente() {
        return LocalDate.now().isBefore(this.fechaFin);
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public abstract Double calcularDescuento(Producto producto);

    /**
     * Calcula el monto que se debe descontar.
     * @param original : Monto sobre el que aplicar descuento.
     * @param porcentaje : Porcentaje del descuento.
     * @return El monto que se debe descontar para aplicar el descuento, 0.0 si el porcentaje esta fuera de rango (0-100)
     */
    protected Double calcularDescuento(Double original, Integer porcentaje) {
        if(porcentaje > 100 || porcentaje < 0) return 0.0;
        Double descuentoCoef = 1.0 - (porcentaje / 100.0);
        return original * descuentoCoef;
    }
}
