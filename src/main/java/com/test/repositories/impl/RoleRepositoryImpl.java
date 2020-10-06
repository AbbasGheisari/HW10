package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.Role;
import com.test.repositories.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class RoleRepositoryImpl extends BaseRepositoryImpl<Role, Long> implements RoleRepository {
    public RoleRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Role> findByTitle(String title) {
        Role role;
        try {
            role = em.createNamedQuery("Role.findByTitle", Role.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            role = null;
        }
        return role != null ? Optional.of(role) : Optional.empty();
    }

    @Override
    public void delete(Long id) {
        super.delete(Role.class, id);
    }
}
