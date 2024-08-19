package ar.unrn.tp.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime fecha;

    private Cliente cliente;

    private Map<Producto, Double> productos;

    private Double montoTotal;

    public Venta(Cliente cliente, Map<Producto, Double> productos, Double montoTotal) {
        this.fecha = LocalDateTime.now();
        this.cliente = cliente;
        this.productos = productos;
        this.montoTotal = montoTotal;
    }

}
