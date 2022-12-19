package dao.hibernate_impl;

import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.hibernate_model.Newspaper;
import dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class NewspaperDaoImpl {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public NewspaperDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    //@Override
    public List<Newspaper> getAll() {
        List<Newspaper> list;
        em = jpaUtil.getEntityManager();
        try {
            list = em.createNamedQuery("HQL_GET_ALL_NEWSPAPERS", Newspaper.class)
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
    public Newspaper get(int id) {
        Newspaper newspaper;
        em = jpaUtil.getEntityManager();
        try {
            newspaper = em.find(Newspaper.class, id);
            if (newspaper == null) {
                log.error(Constantes.NO_NEWSPAPERS_FOUND);
                throw new NotFoundException(Constantes.NO_NEWSPAPERS_FOUND);
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
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
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
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
            em.getTransaction().rollback();
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
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
