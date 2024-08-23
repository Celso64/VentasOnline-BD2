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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescuentoTest {

    private static ClienteService clienteService;
    private static ProductoService productoService;
    private static DescuentoService descuentoService;
    private static VentaService ventaService;

    private static Cliente cliente;
    private static Tarjeta tarjeta;
    private static Producto producto;

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
        producto = productoService.listarProductos().stream().findFirst().get();
    }

    @Test
    @Order(1)
    public void descuentosVencidos() {
        LocalDate inicio = LocalDate.now().minusMonths(2);
        LocalDate fin = LocalDate.now().minusMonths(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> descuentoService.crearDescuento("Acme", inicio, fin, 0.9f));
        assertEquals("Descuento Vencido", exception.getMessage());
    }


    @Test
    public void descuentosPorMarca() {
        final float DESCUENTO = 0.1f;
        Producto p = producto;

        LocalDate inicio = LocalDate.now().minusMonths(2);
        LocalDate fin = LocalDate.now().plusMonths(2);

        descuentoService.crearDescuento("acme", inicio, fin, DESCUENTO);
        Double descuento = descuentoService.calcularDescuentoMarca(p.getId(), "Acme");
        assertEquals((p.getPrecio() * (1.0 - DESCUENTO)), descuento);
    }

    @Test
    @Order(2)
    public void descuentosPorPago() {
        final float DESCUENTO = 0.2f;
        Producto p = producto;

        LocalDate inicio = LocalDate.now().minusMonths(2);
        LocalDate fin = LocalDate.now().plusMonths(2);

        descuentoService.crearDescuentoSobreTotal(tarjeta.getMarca(), inicio, fin, DESCUENTO);
        Double descuentoTarjeta = descuentoService.calcularDescuentoTarjeta(p.getId(), tarjeta.getId());
        assertEquals(119999.99955296516, descuentoTarjeta);
    }

    @Test
    @Order(3)
    public void descuentosPorPagoYMarca() {
        Float descuento = ventaService.calcularMonto(List.of(producto.getId()), tarjeta.getId());
        assertEquals(150_000.0f, descuento);
    }

    @Test
    public void descuentosSuperpuestos() {
        Exception exception = assertThrows(com.objectdb.o._NoResultException.class, () -> {
            descuentoService.crearDescuento("Paladini", LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), 0.1f);
            descuentoService.crearDescuento("Arcor", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 11, 1), 0.1f);
        });
        assertEquals("No matching results for a unique query", exception.getMessage());
    }
}
