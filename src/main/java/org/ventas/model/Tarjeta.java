package org.ventas.model;

import org.ventas.model.util.CodigoSeguridadTarjeta;
import org.ventas.model.util.FechaVencimientoTarjeta;

public class Tarjeta {

    private static IdCounter<Long> idCounter = new LongCounter();

    private final Long id;

    private final Integer numero, codigoSeguridad;

    private final String fechaVencimiento;

    private Double fondos;

    private MarcaTarjeta marca;

    public Tarjeta(Integer numero, Integer codigoSeguridad, String fechaVencimiento, MarcaTarjeta marcaTarjeta) {
        this.id = idCounter.getId();
        this.numero = numero;
        this.codigoSeguridad = new CodigoSeguridadTarjeta(codigoSeguridad).toInteger();
        this.fechaVencimiento = new FechaVencimientoTarjeta(fechaVencimiento).toString();
        this.fondos = 0.0;
        this.marca = marcaTarjeta;
    }

    public Tarjeta(Integer numero, Integer codigoSeguridad, String fechaVencimiento, MarcaTarjeta marcaTarjeta, Double fondos) {
        this(numero, codigoSeguridad, fechaVencimiento, marcaTarjeta);
        this.fondos = fondos;
    }

    public void agregarFondos(Double monto){
        if (monto > 0) throw new IllegalArgumentException("No se puede agregar monto negativo");
        this.fondos += monto;
    }

    public void quitarFondos(Double monto){
        if (monto > 0) throw new IllegalArgumentException("No se puede quitar monto negativo");
        if ((this.fondos-monto) < 0.0 )  throw new IllegalStateException("No hay sufientes fondos");
        this.fondos -= monto;
    }

    public Boolean esMarca(MarcaTarjeta marcaTarjeta){
        return this.marca.equals(marcaTarjeta);
    }

    public Boolean esMarca(String marcaTarjeta){
        return this.marca.equals(new MarcaTarjeta(marcaTarjeta));
    }

    public MarcaTarjeta getMarca() {
        return marca;
    }
}
