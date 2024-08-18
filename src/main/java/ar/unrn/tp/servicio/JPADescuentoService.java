package ar.unrn.tp.servicio;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoTarjeta;
import ar.unrn.tp.modelo.Tarjeta;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class JPADescuentoService implements DescuentoService {

    EntityUtil<DescuentoTarjeta> descuentos = new EntityUtil<>();

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        descuentos.ejecutarTransaccion((em) -> {
            TypedQuery<Tarjeta> tarjetaQuery = em.createQuery("select t from Tarjeta t where t.marca = :marca", Tarjeta.class);
            tarjetaQuery.setParameter("marca", marcaTarjeta);
            Tarjeta tarjeta = tarjetaQuery.getSingleResult();
            DescuentoTarjeta nuevoDescuento = new DescuentoTarjeta(fechaDesde, fechaHasta, (double) porcentaje, tarjeta);
            em.persist(nuevoDescuento);
        });
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {

    }

    @Override
    public List<DescuentoTarjeta> listAllDescuentos() {
        return descuentos.ejecutarQuery((em) -> {
            TypedQuery<DescuentoTarjeta> descuentoQuery = em.createQuery("select d from DescuentoTarjeta d", DescuentoTarjeta.class);
            return descuentoQuery.getResultList();
        });
    }
}
