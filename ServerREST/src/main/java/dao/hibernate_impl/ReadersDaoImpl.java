package dao.hibernate_impl;

import dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import dao.hibernate_model.Reader;

import java.util.List;

public class ReadersDaoImpl {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ReadersDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    //@Override
    public List<Reader> getAll() {
        List<Reader> list = null;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_READERS", Reader.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    //@Override
    public Reader get(int id) {
        Reader reader = null;
        em = jpaUtil.getEntityManager();
        try {
            reader = em.find(Reader.class, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
