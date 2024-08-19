import ar.unrn.servicio.utils.TestEntityUtil;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.servicio.JPAClienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private static ClienteService clienteService;

    @BeforeAll
    public static void init() {
        clienteService = new JPAClienteService(new TestEntityUtil<>(), new TestEntityUtil<>());
    }

    @Test
    public void crearClienteMalConEmailBien() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            clienteService.crearCliente(null, null, "juan@gmail.com", null);
        });
        assertNull(exception.getMessage());
    }

    @Test
    public void crearClienteMal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.crearCliente("Juan", "Perez", "juan.com", "35643654");
        });
        assertEquals("Email Invalido", exception.getMessage());
    }
}
