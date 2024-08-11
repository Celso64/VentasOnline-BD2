package org.ventas.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FechaVencimientoTarjeta {

    private final String fechaVencimiento;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");


    public FechaVencimientoTarjeta(String fechaVencimiento) {

        try {
            LocalDate fecha = LocalDate.parse(fechaVencimiento, formatter);
            LocalDate fechaActual = LocalDate.now();

            if (!fecha.isAfter(fechaActual)) throw new IllegalArgumentException("Tarjeta Vencida");

            this.fechaVencimiento = fechaVencimiento;

        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha/Formato Invalido");
        }
    }

    @Override
    public String toString() {
        return fechaVencimiento;
    }
}
