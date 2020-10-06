package com.test.domains;

import com.test.base.controller.utilities.NoValidDataException;
import com.test.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "tag")
@NamedQueries({
        @NamedQuery(name = "Tag.findAll", query = "select t from Tag t"),
        @NamedQuery(name = "Tag.findByTitle", query = "select t from Tag t where t.title =:title")
})
public class   Tag extends BaseEntity<Long> implements Comparable<Tag> {

    @Transient
    private static final long serialVersionUID = -5915834205584682883L;

    @Transient
    private static long count = 2;

    @Column(nullable = false, unique = true)
    private String title;
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Article> articles = new TreeSet<>();

    public Tag() {
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

    public void addArticle(Article article) {
        articles.add(article);
        article.getTags().add(this);
    }

    @Override
    public String toString() {
        return String.format("%s", getTitle());
    }


    @Override
    public int compareTo(Tag t) {
        return this.getTitle().compareTo(t.getTitle());
    }
}
