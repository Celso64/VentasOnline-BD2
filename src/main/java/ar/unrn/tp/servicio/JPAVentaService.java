package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;

import java.util.List;

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
            List<Descuento> descuentos = descuentoService.listAllDescuentos();

        });
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        return 0;
    }

    @Override
    public List<Venta> ventas() {
        return List.of();
    }
}
