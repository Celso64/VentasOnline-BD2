package ar.unrn.tp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
