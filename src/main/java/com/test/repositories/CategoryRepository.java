package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.Category;

import java.util.Optional;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    void delete(Long id);
}
