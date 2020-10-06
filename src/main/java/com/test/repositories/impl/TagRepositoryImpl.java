package com.test.repositories.impl;

import com.test.base.repositories.impl.BaseRepositoryImpl;
import com.test.domains.Tag;
import com.test.repositories.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class TagRepositoryImpl extends BaseRepositoryImpl<Tag, Long> implements TagRepository {
    public TagRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        Tag tag;
        try {
            tag = em.createNamedQuery("Tag.findByTitle", Tag.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return tag != null ? Optional.of(tag) : Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try {
            Tag tag = em.find(Tag.class, id);
            if (tag != null) {
                em.getTransaction().begin();

                tag.getArticles().forEach(article -> article.getTags().remove(tag));

                em.remove(tag);

                em.getTransaction().commit();
            }
        } catch (Exception exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}
