package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.Category;
import com.test.repositories.CategoryRepository;
import com.test.services.CategoryService;

import java.util.Optional;

public class CategoryServiceImpl extends BaseServiceImpl<Category, Long, CategoryRepository> implements CategoryService {

    public CategoryServiceImpl(CategoryRepository badeRepository) {
        super(badeRepository);
    }

    @Override
    public Optional<Category> findByTitle(String title) {
        return baseRepository.findByTitle(title);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }
}
