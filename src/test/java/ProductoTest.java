import ar.unrn.servicio.utils.TestEntityUtil;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.servicio.JPAProductoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductoTest {

    private static ProductoService productoService;

    @BeforeAll
    public static void init() {
        productoService = new JPAProductoService(new TestEntityUtil<>(), new TestEntityUtil<>(), new TestEntityUtil<>());
    }

    @Test
    public void productoSinDatos() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            productoService.crearMarca("Wilson");
            productoService.crearCategoria("Pelotas");
            Marca marca = productoService.listMarcas().stream().findFirst().get();
            Categoria categoria = productoService.listCategorias().stream().findFirst().get();
            productoService.crearProducto("Pelota de Voley", "Color: Verde Lima", 75_000.0f, categoria.getId(), marca.getId());
        });
        assertNull(exception.getMessage());
    }
}
