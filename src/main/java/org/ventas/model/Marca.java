package org.ventas.model;

import java.util.Objects;

public class Marca {

    private final String nombre;

    public Marca(String nombre) {
        if (Objects.isNull(nombre)) throw new IllegalArgumentException("Nombre Nulo");
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre Vacio");
        this.nombre = nombre.toUpperCase();
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marca marca)) return false;
        return Objects.equals(nombre, marca.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}
