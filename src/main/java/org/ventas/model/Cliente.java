package org.ventas.model;

import org.ventas.model.util.Dni;
import org.ventas.model.util.Email;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cliente {

    private static IdCounter<Long> idCounter = new LongCounter();

    private final Long id;

    private String nombre, apellido, email, dni;

    private final Set<Tarjeta> tarjetas;

    public Cliente(String nombre, String apellido, String email, String dni) {
        this.id = idCounter.getId();
        this.nombre = Objects.requireNonNull(nombre);
        this.apellido = Objects.requireNonNull(apellido);
        this.email = new Email(email).toString();
        this.dni = new Dni(dni).toString();
        this.tarjetas = new HashSet<>();
    }

    public Cliente(String nombre, String apellido, String email, String dni, Set<Tarjeta> tarjetas) {
        this(nombre, apellido, email, dni);
        this.tarjetas.addAll(tarjetas);
    }

    public Cliente(Long id) {
        this.id = id;
        this.tarjetas = null;
    }

    public void agregarTarjeta(Tarjeta tarjeta){
        this.tarjetas.add(tarjeta);
    }

    public void eliminarTarjeta(Tarjeta tarjeta){
        this.tarjetas.remove(tarjeta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Boolean mismoDNI(String dni){
        return this.dni.equals(dni);
    }

    public Long getId() {
        return id;
    }
}
