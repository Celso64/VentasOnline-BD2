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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescuentoTest {

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
    }

    @Test
    public void descuentosVencidos() {
        LocalDate inicio = LocalDate.of(2000, 1, 1);
        LocalDate fin = LocalDate.of(2000, 11, 1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> descuentoService.crearDescuento("Acme", inicio, fin, 0.9f));
        assertEquals("Descuento Vencido", exception.getMessage());
    }


    @Test
    public void descuentosPorMarca() {
        final float DESCUENTO = 0.1f;
        Producto p = productoService.listarProductos().stream().findFirst().get();
        descuentoService.crearDescuento("acme", LocalDate.of(2024, 6, 20), LocalDate.of(2024, 11, 20), DESCUENTO);
        Double descuento = descuentoService.calcularDescuentoMarca(p.getId(), "Acme");
        assertEquals((p.getPrecio() * (1.0 - DESCUENTO)), descuento);
    }

    @Test
    public void descuentosPorPago() {
        final float DESCUENTO = 0.2f;
        Producto p = productoService.listarProductos().stream().findFirst().get();
        descuentoService.crearDescuentoSobreTotal(tarjeta.getMarca(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 11, 1), DESCUENTO);
        Double descuentoTarjeta = descuentoService.calcularDescuentoTarjeta(p.getId(), tarjeta.getId());
        assertEquals(119999.99955296516, descuentoTarjeta);
    }

//    @Test
//    public void descuentosPorPagoYMarca() {
//        descuentoService.agregarDescuento(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), "Acme");
//        descuentoService.agregarDescuento(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 11, 1), tarjeta);
//        var descuento = descuentoService.calcularDescuento(this.cliente, this.tarjeta);
//        assertEquals(174.0, descuento);
//    }
//
//    @Test
//    public void descuentosSuperpuestos() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            descuentoService.agregarDescuento(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), "Acme");
//            descuentoService.agregarDescuento(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 11, 1), tarjeta);
//        });
//        assertEquals("Plazo ocupado", exception.getMessage());
//    }
}
