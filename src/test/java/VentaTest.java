import ar.unrn.servicio.utils.TestEntityUtil;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.servicio.JPAClienteService;
import ar.unrn.tp.servicio.JPADescuentoService;
import ar.unrn.tp.servicio.JPAProductoService;
import ar.unrn.tp.servicio.JPAVentaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VentaTest {

    private static ClienteService clienteService;
    private static ProductoService productoService;
    private static DescuentoService descuentoService;
    private static VentaService ventaService;

    private static Cliente cliente;
    private static Tarjeta tarjeta;

    @BeforeAll
    public static void init() {
        // Seteamos todos los servicios.
        clienteService = new JPAClienteService(new TestEntityUtil<>(), new TestEntityUtil<>());
        productoService = new JPAProductoService(new TestEntityUtil<>(), new TestEntityUtil<>(), new TestEntityUtil<>());
        descuentoService = new JPADescuentoService(new TestEntityUtil<>(), new TestEntityUtil<>(), new TestEntityUtil<>(), new TestEntityUtil<>());
        ventaService = new JPAVentaService(clienteService, productoService, descuentoService, new TestEntityUtil<>());

        //Cargamos datos.
        clienteService.crearCliente("Juan", "Perez", "23645125", "juan@gmail.com");
        cliente = clienteService.listarClientes().stream().findFirst().get();
        clienteService.agregarTarjeta(cliente.getId(), "145 645 654 145", "NARANJA", 1_000_000.0);
        tarjeta = clienteService.listarTarjetas(cliente.getId()).stream().findFirst().get();

        productoService.crearMarca("Acme");
        productoService.crearCategoria("Indumentaria");

        Marca marca = productoService.listMarcas().stream().findFirst().get();
        Categoria categoria = productoService.listCategorias().stream().findFirst().get();

        productoService.crearProducto("Mochila", "XXL", 150_000.0f, categoria.getId(), marca.getId());

        descuentoService.crearDescuento("Acme", LocalDate.of(2024, 6, 24), LocalDate.of(2024, 11, 24), 0.5f);
    }

    @Test
    public void generarVenta(){
        List<Long> idsProductos =
                productoService
                        .listarProductos()
                        .stream()
                        .map(Producto::getId)
                        .toList();

        ventaService.realizarVenta(cliente.getId(), idsProductos, tarjeta.getId());
        Venta ventaReal = ventaService.ventas().stream().findFirst().get();
        Venta ventaEsperada = new Venta();
        ventaEsperada.setMontoTotal(75_000.0);

        assertEquals(ventaEsperada.getMontoTotal(), ventaReal.getMontoTotal());
    }
}