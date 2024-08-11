import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ventas.service.TiendaService;

public class ClienteTest {

    private static TiendaService tiendaService;

    @BeforeAll
    public static void init() {
        tiendaService = new TiendaService();
    }

    @Test
    public void crearClienteMalConEmailBien() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            tiendaService.agregarCliente(null, null, "juan@gmail.com", null);
        });
        assertNull(exception.getMessage());
    }

    @Test
    public void crearClienteMal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tiendaService.agregarCliente("Juan", "Perez", "juan.com", "35643654");
        });
        assertEquals("Email Invalido", exception.getMessage());
    }
}
