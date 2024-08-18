package ar.unrn.tp.servicio;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;

import javax.persistence.TypedQuery;
import java.util.List;

public class JPAClienteService implements ClienteService {

    private final EntityUtil<Cliente> clientes = new EntityUtil<>();
    private final EntityUtil<Tarjeta> tarjetas = new EntityUtil<>();

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        clientes.ejecutarTransaccion((em) -> {
            var cliente = new Cliente(nombre, apellido, email, dni);
            em.persist(cliente);
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        clientes.ejecutarTransaccion((em) -> {
            var cliente = em.find(Cliente.class, idCliente);
            cliente.update(nombre, apellido, email);
            em.persist(cliente);
        });
    }

    @Override
    public List<Cliente> listarClientes() {
        return clientes.ejecutarQuery((em -> {
            TypedQuery<Cliente> clientes = em.createQuery(
                    "select c from Cliente c",
                    Cliente.class);
            clientes.getResultList().forEach(Object::toString); // Esto es para que me de el objeto completo. Como si estuviera EAGER LOAD.
            return clientes.getResultList();
        }));
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca, Double fondos) {
        clientes.ejecutarTransaccion((em) -> {
            Cliente cliente = em.find(Cliente.class, idCliente);
            cliente.agregarTarjeta(new Tarjeta(nro, marca, fondos));
        });
    }

    @Override
    public List<Tarjeta> listarTarjetas(Long idCliente) {
        return tarjetas.ejecutarQuery((em -> {
            TypedQuery<Tarjeta> tarjetas = em.createQuery(
                    "select t from Cliente c join c.tarjetas t where c.id = :id",
                    Tarjeta.class);
            tarjetas.setParameter("id", idCliente);
            tarjetas.getResultList().forEach(System.out::println);
            return tarjetas.getResultList();
        }));
    }
}
