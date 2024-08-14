package org.ventas.repository;

import org.ventas.model.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Boolean.*;

public class Descuentos {

    private final Map<Marca, List<DescuentoMarca>> descuentoMarcas;
    private final Map<MarcaTarjeta, Map<LocalDateTime, DescuentoTarjeta>> descuentoTarjetas;

    public Descuentos() {
        this.descuentoMarcas = new HashMap<>();
        this.descuentoTarjetas = new HashMap<>();
    }

    public void agregarDescuento(Descuento descuento){
        List descMarca = this.descuentoMarcas.values().stream().findFirst().orElse(Collections.EMPTY_LIST);
        List descTarjeta = this.descuentoTarjetas.values().stream().findFirst().orElse(Collections.EMPTY_MAP).values().stream().toList();

        if(haySuperpuesto(descMarca, descuento)) throw new IllegalArgumentException("Plazo ocupado");

        if(descuento instanceof DescuentoMarca) agregarDescuento((DescuentoMarca) descuento);
        else if (descuento instanceof DescuentoTarjeta) agregarDescuento((DescuentoTarjeta) descuento);
    }

    private void agregarDescuento(DescuentoMarca descuento) {
        Objects.requireNonNull(descuento);
        if (this.descuentoMarcas.containsKey(descuento.getMarca())) {
            this.descuentoMarcas
                    .get(descuento.getMarca()).add(descuento);

        } else {
            var lista = new ArrayList<DescuentoMarca>();
            lista.add(descuento);
            this.descuentoMarcas
                    .put(descuento.getMarca(), lista);
        }
    }

    private void agregarDescuento(DescuentoTarjeta descuento) {
            Map<LocalDateTime, DescuentoTarjeta> marca = (this.descuentoTarjetas.containsKey(descuento.getMarca()))
                    ? this.descuentoTarjetas.get(descuento.getMarca())
                    : this.descuentoTarjetas.put(descuento.getMarca(), new TreeMap<>(LocalDateTime::compareTo));

        var m = descuentoTarjetas.get(descuento.getMarca());
        m.put(LocalDateTime.now(), descuento);
    }

    public Double aplicarDescuentos(Collection<Producto> productos, Tarjeta tarjeta) {
        Double totalSinDescuento = productos.stream().mapToDouble(Producto::getPrecio).sum();

        Double descuentoMarcas = productos.stream().mapToDouble(this::aplicarDescuentoMarca).sum();
        Double descuentoTarjeta = productos.stream().mapToDouble(p -> aplicarDescuentoTarjeta(p, tarjeta)).sum();

        return totalSinDescuento - (descuentoMarcas + descuentoTarjeta);
    }

    private Double aplicarDescuentoMarca(Producto producto) {
        var marca = this.descuentoMarcas.keySet().stream().filter(producto::esMarca).findFirst();

        if(marca.isPresent()){
            var descuentos = this.descuentoMarcas.get(marca.get());
            return descuentos.stream().mapToDouble(x -> x.calcularDescuento(producto)).sum();

        }
        return 0.0;
    }

    private Double aplicarDescuentoTarjeta(Producto producto, Tarjeta tarjeta) {
        var descuento = this.descuentoTarjetas.get(tarjeta.getMarca());
        if (!Objects.isNull(descuento)) {
            var clave = descuento.keySet().stream().findFirst();
            if (clave.isPresent()) return descuento.get(clave.get()).calcularDescuento(producto);
        }
        return 0.0;
    }

    private Boolean haySuperpuesto(List<Descuento> descuentos, Descuento descuento){
        return descuentos.stream().anyMatch(x -> x.estaSuperpuesto(descuento.getPeriodo()));
    }
}
