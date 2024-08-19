package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.utils.DescuentosManager;
import ar.unrn.tp.servicio.utils.EntityUtil;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JPAVentaService implements VentaService {

    ClienteService clienteService = new JPAClienteService();
    ProductoService productoService = new JPAProductoService();
    DescuentoService descuentoService = new JPADescuentoService();
    EntityUtil<Venta> ventas = new EntityUtil<>();

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        ventas.ejecutarTransaccion((em) -> {
            Cliente cliente = clienteService.buscarCliente(idCliente);
            Tarjeta tarjeta = cliente.getTarjeta(idTarjeta).orElse(null);
            List<Producto> productoList = productoService.buscarProductos(productos);
            DescuentosManager descuentos = new DescuentosManager(descuentoService.listAllDescuentos());

            Map<Producto, Double> listaProductos = new HashMap<>(productoList.size());
            Double total = 0.0;
            for (Producto p : productoList) {
                DescuentoMarca descuentoMarca = descuentos.getDescuentoMarca(p.getMarca().getNombre());
                DescuentoTarjeta descuentoTarjeta = descuentos.getDescuentoTarjeta(cliente.getTarjeta(idTarjeta).orElse(new Tarjeta("NULL")).getMarca());

                Double precio = (Objects.isNull(descuentoMarca)) ? p.getPrecio() : descuentoMarca.calcularDescuento(p);
                precio = (Objects.isNull(descuentoTarjeta)) ? precio : descuentoTarjeta.calcularDescuento(p);

                listaProductos.put(p, precio);
                total += precio;
            }

            Venta nuevaVenta = new Venta(cliente, listaProductos, total);
            em.persist(nuevaVenta);
        });
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        return 0;
    }

    @Override
    public List<Venta> ventas() {
        return ventas.ejecutarQuery((em) -> {
            TypedQuery<Venta> ventaQuery = em.createQuery("select v from Venta v", Venta.class);
            List<Venta> ventaList = ventaQuery.getResultList();
            ventaList.forEach(x -> x.getCliente().toString());
            ventaList.forEach(x -> x.getProductos().toString());
            return ventaList;
        });
    }
}
