package ar.unrn.tp.main;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.servicio.JPAClienteService;
import ar.unrn.tp.servicio.JPADescuentoService;
import ar.unrn.tp.servicio.JPAProductoService;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ClienteService clientes = new JPAClienteService();
        ProductoService productoService = new JPAProductoService();
        DescuentoService descuentoService = new JPADescuentoService();

        clientes.crearCliente("Juan", "Perez", "56412320", "juan@gmail.com");
        clientes.agregarTarjeta(1L, "545 342 453 653", "NARANJA", 1_250_000.0);

        productoService.crearCategoria("Ropa");
        productoService.crearMarca("Nike");

        descuentoService.crearDescuentoSobreTotal("NARANJA",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 11, 1),
                0.1f);

        List<Marca> marcas = productoService.listMarcas();
        List<Categoria> categorias = productoService.listCategorias();

        Long marcaID = marcas.get(0).getId();
        Long categoriaID = categorias.get(0).getId();

        productoService.crearProducto("Botines", "Botines de Futbol talla 44", 1250.0f, categoriaID, marcaID);


        clientes.listarClientes().forEach(System.out::println);
        productoService.listarProductos().forEach(System.out::println);
        descuentoService.listAllDescuentos().forEach(System.out::println);

    }
}
