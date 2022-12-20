package dao.hibernate_impl;

import dao.excepciones.DatabaseException;
import dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import dao.hibernate_model.Reader;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ReadersDaoImpl {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ReadersDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    //@Override
    public List<Reader> getAll() {
        List<Reader> list;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList();

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    //@Override
    public Reader get(int id) {
        Reader reader;
        em = jpaUtil.getEntityManager();
        try {
            reader = em.find(Reader.class, id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    //@Override
    public Reader save(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reader);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    //@Override
    public Reader update(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reader);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return reader;
    }

    //@Override
    public void delete(Reader reader) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(reader));
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
