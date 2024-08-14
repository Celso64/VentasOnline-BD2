import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ventas.model.*;
import org.ventas.service.TiendaService;

import java.time.LocalDate;

public class DescuentoTest {

    private static TiendaService tiendaService;

    private Long cliente;
    private Long producto;
    private Tarjeta tarjeta;

    @BeforeEach
    public void cargarDatos() {
        tiendaService = new TiendaService();
        this.cliente = tiendaService.agregarCliente("Juan", "Perez", "juan@gmail.com", "23645125");
        this.producto = tiendaService.agregarProducto("Mochila", "XXL", "Acme", 200.0);
        this.tarjeta = tiendaService.asociarTarjeta(this.cliente, "654_456_654", 123, "10/24", "Naranja").get();
        tiendaService.agregarAlCarrito(this.cliente, this.producto);
    }

    @Test
    public void descuentosVencidos() {
        LocalDate inicio = LocalDate.of(2000, 1, 1);
        LocalDate fin = LocalDate.of(2000, 11, 1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> tiendaService.agregarDescuento(inicio, fin, "Acme"));
        assertEquals("Descuento Vencido", exception.getMessage());
    }


    @Test
    public void descuentosPorMarca() {
        tiendaService.agregarDescuento(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 11, 1), "Acme");
        var descuento = tiendaService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(190.0, descuento);
    }

    @Test
    public void descuentosPorPago() {
        tiendaService.agregarDescuento(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 11, 1), tarjeta);
        var descuento = tiendaService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(184.0, descuento);
    }

    @Test
    public void descuentosPorPagoYMarca() {
        tiendaService.agregarDescuento(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 9, 10), "Acme");
        tiendaService.agregarDescuento(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 11, 1), tarjeta);
        var descuento = tiendaService.calcularDescuento(this.cliente, this.tarjeta);
        assertEquals(174.0, descuento);
    }

    // TODO Verificar que no sea posible crear un descuento con fechas validez superpuestas.
    @Test
    public void descuentosSuperpuestos() {

    }
}
