import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ventas.service.TiendaService;

public class ProductoTest {

    private static TiendaService tiendaService;

    @BeforeAll
    public static void init() {
        tiendaService = new TiendaService();
    }

    @Test
    public void productoSinDatos() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            tiendaService.agregarProducto("Mochila", "", "Wilson", null);
        });
        assertNull(exception.getMessage());
    }
}
