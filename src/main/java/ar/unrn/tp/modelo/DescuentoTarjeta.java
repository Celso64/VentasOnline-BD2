package ar.unrn.tp.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class DescuentoTarjeta extends Descuento {

    private Tarjeta tarjeta;

    public DescuentoTarjeta(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    public String getMarca() {
        return this.tarjeta.getMarca();
    }

    @Override
    public Double calcularDescuento(Producto producto) {
        return super.calcularDescuento(producto.getPrecio(), super.getPorcentajeDescuento());
    }

    @Override
    public String toString() {
        return "DescuentoTarjeta{" +
                "tarjeta=" + tarjeta +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", porcentajeDescuento=" + porcentajeDescuento +
                '}';
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        super.setPorcentajeDescuento(porcentajeDescuento);
    }

    public Double getPorcentajeDescuento() {
        return super.getPorcentajeDescuento();
    }
}
