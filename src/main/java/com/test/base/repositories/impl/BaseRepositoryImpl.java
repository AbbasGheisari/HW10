package com.test.base.repositories.impl;

import com.test.base.domains.BaseEntity;
import com.test.base.repositories.BaseRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl<E extends BaseEntity<PK>, PK extends Serializable> implements BaseRepository<E, PK> {
    protected final EntityManager em;

    public BaseRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<E> save(E e) {
        try {
            em.getTransaction().begin();

            /*em.flush();
            em.clear();*/

            if (!em.contains(e))
                em.persist(e);

            em.getTransaction().commit();
            return Optional.of(e);
        } catch (Exception exception) {
            exception.printStackTrace();
            em.getTransaction().rollback();
        }
        return Optional.empty();
    }

    @Override
    public Optional<E> update(E e) {
        try {
            em.getTransaction().begin();

            /*em.flush();
            em.clear();*/

            if (em.contains(e))
                e = em.merge(e);

            em.getTransaction().commit();
            return Optional.of(e);
        } catch (Exception exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<E> findById(Class<E> c, PK id) {
        E e = em.find(c, id);
        return e != null ? Optional.of(e) : Optional.empty();
    }

    @Override
    public void delete(Class<E> c, PK id) {
        try {
            E e = em.find(c, id);
            if (e != null) {
                em.getTransaction().begin();

                /*em.flush();
                em.clear();*/

                if (em.contains(e))
                    em.remove(e);
                else em.merge(e);

                em.getTransaction().commit();
            }
        } catch (Exception exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public List<E> findAll(Class<E> c, String query) {
        return em.createNamedQuery(query, c).getResultList();
    }
}
