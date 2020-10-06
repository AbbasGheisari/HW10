package com.test.domains;

import com.test.base.controller.utilities.NoValidDataException;
import com.test.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "select c from Category c"),
        @NamedQuery(name = "Category.findByTitle", query = "select c from Category c where c.title =:title")
})
public class Category extends BaseEntity<Long> implements Comparable<Category> {

    @Transient
    private static final long serialVersionUID = -6796340476955577977L;

    @Transient
    private static long count = 2;

    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String description;
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Article> articles = new TreeSet<>();

    public Category() {
        this.setId(count);
        count++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws NoValidDataException {
        if (!title.matches("[a-zA-Z\\s.,&\\d\\(\\)]{3,}")) {
            throw new NoValidDataException("Title");
        }
        this.title = title;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws NoValidDataException {
        if (!description.matches("[a-zA-Z\\s.,&\\d\\(\\)]{10,}")) {
            throw new NoValidDataException("Description");
        }
        this.description = description;
    }

    public void addArticle(Article article) {
        articles.add(article);
        article.getCategories().add(this);
    }

    @Override
    public String toString() {
        return String.format("%s%nDescription: %s", getTitle(), getDescription());
    }


    @Override
    public int compareTo(Category c) {
        return this.getTitle().compareTo(c.getTitle());
    }
}
