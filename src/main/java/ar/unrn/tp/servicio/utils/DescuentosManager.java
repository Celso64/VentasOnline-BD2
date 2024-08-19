package ar.unrn.tp.servicio.utils;

import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoMarca;
import ar.unrn.tp.modelo.DescuentoTarjeta;

import java.util.List;

public class DescuentosManager {
    private List<DescuentoTarjeta> descuentoTarjetas;
    private List<DescuentoMarca> descuentoMarcas;

    public DescuentosManager(List<Descuento> descuentos) {
        this.descuentoMarcas = descuentos.stream().filter(x -> x instanceof DescuentoMarca).map(x -> (DescuentoMarca) x).toList();
        this.descuentoTarjetas = descuentos.stream().filter(x -> x instanceof DescuentoTarjeta).map(x -> (DescuentoTarjeta) x).toList();
    }

    public DescuentoMarca getDescuentoMarca(String marcaProducto) {
        return this.descuentoMarcas.stream().filter(x -> x.esMarca(marcaProducto)).findFirst().orElse(null);
    }

    public DescuentoTarjeta getDescuentoTarjeta(String marcaTarjeta) {
        return this.descuentoTarjetas.stream().filter(x -> x.esMarca(marcaTarjeta)).findFirst().orElse(null);
    }
}
