import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ventas.service.TiendaService;

public class ProductoTest {

    private static TiendaService tiendaService;

    @BeforeAll
    public static void init(){
        tiendaService = new TiendaService();
    }

    // TODO Verificar que no sea posible crear un Producto sin categoría, descripción y precio.
    @Test
    public void productoSinDatos(){

    }
}
