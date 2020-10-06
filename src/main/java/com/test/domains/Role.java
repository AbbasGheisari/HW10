package com.test.domains;

import com.test.base.controller.utilities.NoValidDataException;
import com.test.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "role")
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "select r from Role r"),
        @NamedQuery(name = "Role.findByTitle", query = "select r from Role r where r.title =:title")
})
public class Role extends BaseEntity<Long> implements Comparable<Role> {

    @Transient
    private static final long serialVersionUID = -1163425666831025092L;

    @Transient
    private static long count = 2;

    @Column(nullable = false, unique = true)
    private String title;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new TreeSet<>();

    public Role() {
        this.setId(count);
        count++;
    }

    public Role(String title) {
        this();
        this.title = title;
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

    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return String.format("%s", getTitle());
    }


    @Override
    public int compareTo(Role r) {
        return this.getTitle().compareTo(r.getTitle());
    }
}
