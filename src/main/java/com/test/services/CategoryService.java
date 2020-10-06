package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.Category;

import java.util.Optional;

public interface CategoryService extends BaseService<Category, Long> {

    Optional<Category> findByTitle(String title);

    void delete(Long id);
}
