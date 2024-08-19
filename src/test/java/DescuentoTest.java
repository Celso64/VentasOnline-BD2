import ar.unrn.servicio.utils.TestEntityUtil;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicio.JPADescuentoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescuentoTest {

    private static DescuentoService descuentoService;

    private Long cliente;
    private Tarjeta tarjeta;

    @BeforeAll
    public static void init() {
        descuentoService = new JPADescuentoService(new TestEntityUtil<>(), new TestEntityUtil<>(), new TestEntityUtil<>());
    }

//    @BeforeEach
//    public void cargarDatos() {
//        descuentoService = new TiendaService();
//        this.cliente = descuentoService.agregarCliente("Juan", "Perez", "juan@gmail.com", "23645125");
//        var producto = descuentoService.agregarProducto("Mochila", "XXL", "Acme", 200.0);
//        this.tarjeta = descuentoService.asociarTarjeta(this.cliente, "654_456_654", 123, "10/24", "Naranja").get();
//        descuentoService.agregarAlCarrito(this.cliente, producto);
//    }

    @Test
    public void descuentosVencidos() {
        LocalDate inicio = LocalDate.of(2000, 1, 1);
        LocalDate fin = LocalDate.of(2000, 11, 1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> descuentoService.agregarDescuento(inicio, fin, "Acme"));
        assertEquals("Descuento Vencido", exception.getMessage());
    }


    @Test
    public void descuentosPorMarca() {
        descuentoService.agregarDescuento(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 11, 1), "Acme");
        var descuento = descuentoService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(190.0, descuento);
    }

    @Test
    public void descuentosPorPago() {
        descuentoService.agregarDescuento(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 11, 1), tarjeta);
        var descuento = descuentoService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(184.0, descuento);
    }

    @Test
    public void descuentosPorPagoYMarca() {
        descuentoService.agregarDescuento(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), "Acme");
        descuentoService.agregarDescuento(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 11, 1), tarjeta);
        var descuento = descuentoService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(174.0, descuento);
    }

    // TODO Verificar que no sea posible crear un descuento con fechas validez superpuestas.
    @Test
    public void descuentosSuperpuestos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            descuentoService.agregarDescuento(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), "Acme");
            descuentoService.agregarDescuento(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 11, 1), tarjeta);
        });
        assertEquals("Plazo ocupado", exception.getMessage());
    }
}
