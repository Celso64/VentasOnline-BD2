package ar.unrn.tp.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DescuentoMarca extends Descuento {

    @Id
    @GeneratedValue
    private Long id;

    private Marca marca;

    public DescuentoMarca(LocalDate fechaInicio, LocalDate fechaFin, Marca marca, Double porcentaje) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return (this.esMarca(producto.getMarca().getNombre()))
                ? (super.calcularDescuento(producto.getPrecio(), super.getPorcentajeDescuento()))
                : 0.0;
    }

    public Boolean esMarca(String marca) {
        return this.marca.esMarca(marca);
    }

}
