import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.unrn.tp.modelo.Tarjeta;
import org.ventas.service.TiendaService;

public class VentaTest {

    private TiendaService tiendaService;

    private Long cliente;
    private Tarjeta tarjeta;

    @BeforeEach
    public void cargarDatos() {
        tiendaService = new TiendaService();
        this.cliente = tiendaService.agregarCliente("Juan", "Perez", "juan@gmail.com", "23645125");
        var producto = tiendaService.agregarProducto("Mochila", "XXL", "Acme", 200.0);
        this.tarjeta = tiendaService.asociarTarjeta(this.cliente, "654_456_654", 123, "10/24", "Naranja").get();
        tiendaService.agregarAlCarrito(this.cliente, producto);
    }

    // TODO Realizar el pago y verificar que se genere la venta u orden de pago.
    @Test
    public void generarVenta(){
        tiendaService.pagarCarrito(cliente, tarjeta);
    }
}