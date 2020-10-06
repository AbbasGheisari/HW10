package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.SampleTest;
import com.test.repositories.SampleTestRepository;
import com.test.services.SampleTestService;

import java.util.List;

public class SampleTestServiceImpl extends BaseServiceImpl<SampleTest,Long,SampleTestRepository> implements SampleTestService{


    public SampleTestServiceImpl(SampleTestRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<SampleTest> findAll() {
        return baseRepository.findAll();
    }
}
