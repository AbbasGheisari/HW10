package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.Article;
import com.test.repositories.ArticleRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class ArticleRepositoryImpl extends BaseRepositoryImpl<Article, Long> implements ArticleRepository {

    public ArticleRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        Article article;
        try {
            article = em.createNamedQuery("Article.findByTitle", Article.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            article = null;
        }
        return article != null ? Optional.of(article) : Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try {
            Article article = em.find(Article.class, id);
            if (article != null) {
                em.getTransaction().begin();

                article.getWriters().forEach(writer -> writer.getArticles().remove(article));
                article.getCategories().forEach(category -> category.getArticles().remove(article));
                article.getTags().forEach(tag -> tag.getArticles().remove(article));

                em.remove(article);

                em.getTransaction().commit();
            }
        } catch (Exception exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}
