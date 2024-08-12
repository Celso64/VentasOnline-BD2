import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ventas.model.Cliente;
import org.ventas.model.Producto;
import org.ventas.model.Tarjeta;
import org.ventas.service.TiendaService;

public class DescuentoTest {

    private static TiendaService tiendaService;

    private Long cliente;
    private Long producto;
    private Tarjeta tarjeta;

    @BeforeAll
    public static void init(){
        tiendaService = new TiendaService();
    }

    @BeforeEach
    public void cargarDatos(){
        this.cliente = tiendaService.agregarCliente("Juan", "Perez", "juan@gmail.com", "23645125");
        this.producto = tiendaService.agregarProducto("Mochila", "XXL", "Wilson", 200_000.0);
        this.tarjeta = tiendaService.asociarTarjeta(this.cliente, "654_456_654", 123, "11/24", "Naranja").get();
    }

    // TODO Calcular el monto total del carrito seleccionado sin descuentos vigentes, pero con descuentos que ya caducaron.
    @Test
    public void descuentosVencidos(){

    }

    //TODO Calcular el monto total del carrito con un descuento vigente para los productos marca Acme.
    @Test
    public void descuentosPorMarca(){

    }

    // TODO Calcular el monto total del carrito con un descuento vigente del tipo de Medio de pago.
    @Test
    public void descuentosPorPago(){

    }

    // TODO Calcular el monto total del carrito con dos descuentos vigentes, sobre productos marca Comarca y para tarjeta de crédito MemeCard.
    @Test
    public void descuentosPorPagoYMarca(){

    }

    // TODO Verificar que no sea posible crear un descuento con fechas validez superpuestas.
    @Test
    public void descuentosSuperpuestos(){

    }
}
