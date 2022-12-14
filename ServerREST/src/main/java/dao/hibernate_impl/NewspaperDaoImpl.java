package dao.hibernate_impl;

import dao.hibernate_model.Newspaper;
import dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class NewspaperDaoImpl {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public NewspaperDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    //@Override
    public List<Newspaper> getAll() {
        List<Newspaper> list = null;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
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
    public Newspaper get(int id) {
        Newspaper newspaper = null;
        em = jpaUtil.getEntityManager();
        try {
            newspaper = em.find(Newspaper.class, id);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return newspaper;
    }

    //@Override
    public Newspaper save(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newspaper);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return newspaper;
    }

    //@Override
    public Newspaper update(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(newspaper);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return newspaper;
    }

    //@Override
    public void delete(Newspaper newspaper) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(newspaper));
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
