import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ventas.service.TiendaService;

public class DescuentoTest {

    private static TiendaService tiendaService;

    @BeforeAll
    public static void init(){
        tiendaService = new TiendaService();
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
