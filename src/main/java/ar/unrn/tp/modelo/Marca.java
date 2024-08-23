package ar.unrn.tp.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Marca {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public Marca(@NonNull String nombre) {
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre Vacio");
        this.nombre = nombre.toUpperCase();
    }

    public Boolean esMarca(String marcaNombre) {
        return this.nombre.equals(marcaNombre.toUpperCase());
    }
}
