package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.SampleTest;
import com.test.repositories.SampleTestRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class SampleTestRepositoryImpl extends BaseRepositoryImpl<SampleTest,Long> implements SampleTestRepository {

    public SampleTestRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<SampleTest> findAll() {
        return em.createNamedQuery("SampleTest.findAll",SampleTest.class)
                .getResultList();
    }



}
