package ar.unrn.tp.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class DescuentoTarjeta extends Descuento {

    @Id
    @GeneratedValue
    private Long id;

    private Tarjeta tarjeta;

    public DescuentoTarjeta(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return super.calcularDescuento(producto.getPrecio(), super.getPorcentajeDescuento());
    }


    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        super.setPorcentajeDescuento(porcentajeDescuento);
    }

    public Double getPorcentajeDescuento() {
        return super.getPorcentajeDescuento();
    }

    public Boolean esMarca(String marcaTarjeta) {
        return this.tarjeta.esMarca(marcaTarjeta);
    }

    @Override
    public void agregarDescuentoTarjeta(List<DescuentoTarjeta> descuentoTarjetas) {
        descuentoTarjetas.add(this);
    }
}
