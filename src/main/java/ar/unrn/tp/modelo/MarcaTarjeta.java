package ar.unrn.tp.modelo;

import java.util.Objects;

public class MarcaTarjeta {

    private final String nombre;

    public MarcaTarjeta(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarcaTarjeta that)) return false;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
