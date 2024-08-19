package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.servicio.utils.EntityUtil;
import lombok.NonNull;

import javax.persistence.TypedQuery;
import java.util.List;

public class JPAProductoService implements ProductoService {

    private final EntityUtil<Producto> productos;
    private final EntityUtil<Categoria> categorias;
    private final EntityUtil<Marca> marcas;

    public JPAProductoService(@NonNull EntityUtil<Producto> productos, @NonNull EntityUtil<Categoria> categorias, @NonNull EntityUtil<Marca> marcas) {
        this.productos = productos;
        this.categorias = categorias;
        this.marcas = marcas;
    }

    @Override
    public void crearMarca(String nombre) {
        marcas.ejecutarTransaccion((em) -> {
            Marca nuevaMarca = new Marca(nombre);
            em.persist(nuevaMarca);
        });
    }

    @Override
    public void crearCategoria(String nombre) {
        categorias.ejecutarTransaccion((em) -> {
            Categoria nuevaCategoria = new Categoria(nombre);
            em.persist(nuevaCategoria);
        });
    }

    @Override
    public List<Marca> listMarcas() {
        return marcas.ejecutarQuery((em) -> {
            TypedQuery<Marca> marcaQuery = em.createQuery("select m from Marca m", Marca.class);
            return marcaQuery.getResultList();
        });
    }

    @Override
    public List<Categoria> listCategorias() {
        return categorias.ejecutarQuery((em) -> {
            TypedQuery<Categoria> CategoriaQuery = em.createQuery("select c from Categoria c", Categoria.class);
            return CategoriaQuery.getResultList();
        });
    }

    @Override
    public void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long idMarca) {
        productos.ejecutarTransaccion((em) -> {
            Marca marca = em.find(Marca.class, idMarca);
            Categoria categoria = em.find(Categoria.class, IdCategoria);

            Producto nuevoProducto = new Producto(codigo, descripcion, marca, categoria, (double) precio);
            em.persist(nuevoProducto);
        });
    }

    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long IdCategoría, Long idMarca) {
        productos.ejecutarTransaccion((em) -> {
            Producto producto = em.find(Producto.class, idProducto);
            Marca nuevaMarca = em.find(Marca.class, idMarca);
            Categoria nuevaCategoria = em.find(Categoria.class, IdCategoría);
            producto.update(new Producto(codigo, descripcion, nuevaMarca, nuevaCategoria, (double) precio));
        });
    }

    @Override
    public List<Producto> listarProductos() {
        return productos.ejecutarQuery((em) -> {
            TypedQuery<Producto> productosQuery = em.createQuery("select p from Producto p", Producto.class);
            return productosQuery.getResultList();
        });
    }

    @Override
    public List<Producto> buscarProductos(List<Long> idsProductos) {
        return productos.ejecutarQuery((em) -> {
            TypedQuery<Producto> productosQuery = em.createQuery("select p from Producto p where p.id in :ids", Producto.class);
            productosQuery.setParameter("ids", idsProductos);
            return productosQuery.getResultList();
        });
    }
}
