package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.Article;
import com.test.repositories.ArticleRepository;
import com.test.services.ArticleService;

import java.util.Optional;

public class ArticleServiceImpl extends BaseServiceImpl<Article, Long, ArticleRepository> implements ArticleService {
    public ArticleServiceImpl(ArticleRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Article> findByTitle(String name) {
        return baseRepository.findByTitle(name);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }
}
