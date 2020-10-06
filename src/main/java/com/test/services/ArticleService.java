package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.Article;

import java.util.Optional;

public interface ArticleService extends BaseService<Article, Long> {

    Optional<Article> findByTitle(String title);

    void delete(Long id);
}
