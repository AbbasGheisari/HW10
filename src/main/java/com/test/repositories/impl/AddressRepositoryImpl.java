package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.Address;
import com.test.repositories.AddressRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class AddressRepositoryImpl extends BaseRepositoryImpl<Address,Long> implements AddressRepository {

    public AddressRepositoryImpl(EntityManager em){
        super(em);
    }


    @Override
    public Optional<Address> findByUserName(String userName) {
        Address address;
        try {
            address = em.createNamedQuery("Address.findByUsername", Address.class)
                    .setParameter("userName",userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            address = null;
        }
        return address != null ? Optional.of(address) : Optional.empty();
    }

}
