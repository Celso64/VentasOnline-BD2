package ar.unrn.tp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    private String codigo, descripcion;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Marca marca;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Categoria categoria;

    private Double precio;

    public Producto(@NonNull String nombre, @NonNull String descripcion, @NonNull Marca marca, @NonNull Categoria categoria, @NonNull Double precio) {
        this.codigo = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
    }

    public void update(Producto productoNuevo) {
        this.codigo = productoNuevo.getCodigo();
        this.descripcion = productoNuevo.getDescripcion();
        this.marca = productoNuevo.getMarca();
        this.categoria = productoNuevo.getCategoria();
        this.precio = productoNuevo.getPrecio();
    }
}
