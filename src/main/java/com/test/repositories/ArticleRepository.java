package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.Article;

import java.util.Optional;

public interface ArticleRepository extends BaseRepository<Article, Long> {
    Optional<Article> findByTitle(String title);

    void delete(Long id);
}
