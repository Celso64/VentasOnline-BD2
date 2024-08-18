package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.util.CodigoSeguridadTarjeta;
import ar.unrn.tp.modelo.util.FechaVencimientoTarjeta;
import ar.unrn.tp.modelo.util.NumeroTarjeta;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue
    private Long id;

    private String numero, marca;

    private Double fondos;


    public Tarjeta(String numero, String marcaTarjeta) {
        this.numero = new NumeroTarjeta(numero).toString();
        this.fondos = 0.0;
        this.marca = new MarcaTarjeta(marcaTarjeta).toString().toUpperCase();
    }

    public Tarjeta(String numero, String marcaTarjeta, Double fondos) {
        this(numero, marcaTarjeta);
        this.fondos = fondos;
    }

    public void agregarFondos(Double monto) {
        if (monto > 0) throw new IllegalArgumentException("No se puede agregar monto negativo");
        this.fondos += monto;
    }

    public void quitarFondos(Double monto) {
        if (monto > 0) throw new IllegalArgumentException("No se puede quitar monto negativo");
        if ((this.fondos - monto) < 0.0) throw new IllegalStateException("No hay sufientes fondos");
        this.fondos -= monto;
    }

    public Boolean esMarca(String marcaTarjeta) {
        return this.marca.equals(marcaTarjeta);
    }

}
