import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ventas.service.TiendaService;

public class VentaTest {

    private static TiendaService tiendaService;

    @BeforeAll
    public static void init(){
        tiendaService = new TiendaService();
    }

    // TODO Realizar el pago y verificar que se genere la venta u orden de pago.
    @Test
    public void generarVenta(){

    }
}