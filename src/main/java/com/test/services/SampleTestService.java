package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.SampleTest;

import java.util.List;

public interface SampleTestService extends BaseService<SampleTest,Long> {

    List<SampleTest> findAll();

}
