package org.ventas.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Venta {

    private static IdCounter<Long> idCounter = new LongCounter();

    private final Long id;

    private final LocalDateTime fecha;

    private final Cliente cliente;

    private final Map<Producto, Double> productos;

    private final Double montoTotal;

    public Venta(Cliente cliente, Map<Producto, Double> productos, Double montoTotal) {
        this.id = idCounter.getId();
        this.fecha = LocalDateTime.now();
        this.cliente = cliente;
        this.productos = productos;
        this.montoTotal = montoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venta venta)) return false;
        return Objects.equals(id, venta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "venta : { id : " + this.id + ", fecha : " + fecha +
                ", cliente : " + cliente +
                ", productos : " + productos +
                ", montoTotal : " + montoTotal + " }";
    }
}
