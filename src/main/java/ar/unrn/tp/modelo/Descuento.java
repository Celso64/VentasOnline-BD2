package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.Porcentaje;
import ar.unrn.tp.modelo.util.RangoFechas;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public abstract class Descuento {

    @Id
    @GeneratedValue
    protected Long id;
    protected LocalDate fechaInicio, fechaFin;
    protected Double porcentajeDescuento;

    protected Descuento(LocalDate fechaInicio, LocalDate fechaFin, Double porcentajeDescuento) {
        var rango = new RangoFechas(fechaInicio, fechaFin);
        this.fechaInicio = rango.getInicio();
        this.fechaFin = rango.getFin();
        this.porcentajeDescuento = new Porcentaje(porcentajeDescuento).getCoeficiente();
    }

    public Boolean estaVigente() {
        return LocalDate.now().isBefore(this.fechaFin);
    }


    public abstract Double calcularDescuento(Producto producto);

    /**
     * Calcula el monto que se debe descontar.
     * @param original : Monto sobre el que aplicar descuento.
     * @param porcentaje : Porcentaje del descuento.
     * @return El monto que se debe descontar para aplicar el descuento, 0.0 si el porcentaje esta fuera de rango (0-100)
     */
    protected Double calcularDescuento(Double original, Double porcentaje) {
        if (this.estaVencido()) throw new IllegalStateException("Descuento Vencido");
        if(porcentaje > 100 || porcentaje < 0) return 0.0;
        return original * porcentaje;
    }

    private Boolean estaVencido(){
        return this.fechaFin.isBefore(LocalDate.now());
    }

    public Boolean estaSuperpuesto(RangoFechas rangoFechas){
        return new RangoFechas(this.fechaInicio, this.fechaFin).seSuperpone(rangoFechas.getInicio(), rangoFechas.getFin());
    }


    public RangoFechas getPeriodo(){
        return new RangoFechas(this.fechaInicio, this.fechaFin);
    }
}

