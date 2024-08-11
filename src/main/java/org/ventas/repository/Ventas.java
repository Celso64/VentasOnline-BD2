package org.ventas.repository;

import org.ventas.model.Venta;

import java.util.HashSet;
import java.util.Set;

public class Ventas {

    private final Set<Venta> ventas;

    public Ventas() {
        this.ventas = new HashSet<>();
    }

    public void agregarVenta(Venta venta) {
        this.ventas.add(venta);
    }

    @Override
    public String toString() {
        var ventasString = this.ventas
                .stream()
                .map(Venta::toString)
                .reduce("", (anterior, actual) -> "\t" + anterior + "\n" + actual);

        return "ventas: {" + ventasString + '}';
    }
}
