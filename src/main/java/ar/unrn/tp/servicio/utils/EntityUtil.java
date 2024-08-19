package ar.unrn.tp.servicio.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityUtil<T> {
    protected String nombre = "objectdb:myDbTestFile.tmp;drop";
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(nombre);

    public void ejecutarTransaccion(Consumer<EntityManager> bloqueDeCodigo) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            bloqueDeCodigo.accept(entityManager);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (!Objects.isNull(entityManager) && entityManager.isOpen())
                entityManager.close();
        }
    }

    public List<T> ejecutarQuery(Function<EntityManager, List<T>> bloqueDeCodigo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            var entidades = bloqueDeCodigo.apply(entityManager);
            tx.commit();
            return entidades;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (!Objects.isNull(entityManager) && entityManager.isOpen())
                entityManager.close();
        }
    }

    public T ejecutarIndividualQuery(Function<EntityManager, T> bloqueDeCodigo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();
            var entidades = bloqueDeCodigo.apply(entityManager);
            tx.commit();
            return entidades;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (!Objects.isNull(entityManager) && entityManager.isOpen())
                entityManager.close();
        }
    }

}
