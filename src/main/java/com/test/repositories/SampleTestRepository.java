package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.SampleTest;

import java.util.List;


public interface SampleTestRepository extends BaseRepository<SampleTest,Long> {


    List<SampleTest> findAll();

}




