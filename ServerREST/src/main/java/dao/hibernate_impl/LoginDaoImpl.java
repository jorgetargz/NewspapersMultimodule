package dao.hibernate_impl;

import dao.excepciones.DatabaseException;
import dao.hibernate_model.Login;
import dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginDaoImpl {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public LoginDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    //@Override
    public Login get(String username) {
        Login login;
        em = jpaUtil.getEntityManager();
        try {
            login = em.find(Login.class, username);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return login;
    }

    //@Override
    public Login save(Login login) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(login);
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
        return login;
    }

    public void delete(Login login) {
        em = jpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(login));
            em.remove(em.merge(login.getReader()));
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