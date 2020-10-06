package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.Category;
import com.test.repositories.CategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category, Long> implements CategoryRepository {
    public CategoryRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Category> findByTitle(String title) {
        Category category;
        try {
            category = em.createNamedQuery("Category.findByTitle", Category.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            category = null;
        }
        return category != null ? Optional.of(category) : Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try {
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.getTransaction().begin();

                category.getArticles().forEach(article -> article.getCategories().remove(category));

                em.remove(category);

                em.getTransaction().commit();
            }
        } catch (Exception exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}
