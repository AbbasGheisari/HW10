package com.test.domains;

import com.test.base.controller.utilities.NoValidDataException;
import com.test.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findByUserName", query = "select u from User u where u.userName =:userName"),
        @NamedQuery(name = "User.findAllExceptMe", query = "select u from User u where u.userName <>:userName")
})
public class User extends BaseEntity<Long> implements Comparable<User> {

    @Transient
    private static final long serialVersionUID = 5080115020553837541L;

    @Transient
    private static long count = 2;

    @Column(nullable = false, unique = true, updatable = false)
    private String userName;
    @Column(nullable = false, unique = true, updatable = false)
    private String nationalCode;
    @Column(nullable = false, updatable = false)
    private String birthDay;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_article", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "article_id")})
    private Set<Article> articles = new TreeSet<>();
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "Id")
    private Address address;


    public User() {
        this.setId(count);
        count++;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws NoValidDataException {
        if (!userName.matches("(\\w\\d*){3,}")) {
            throw new NoValidDataException("Username");
        }
        this.userName = userName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) throws NoValidDataException {
        if (!nationalCode.matches("\\d{10}")) {
            throw new NoValidDataException("National Code");
        }
        this.nationalCode = nationalCode;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) throws NoValidDataException {
        if (!birthDay.matches("[12][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])")) {
            throw new NoValidDataException("Birthday");
        }
        this.birthDay = birthDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoValidDataException {
        if (!password.matches("[a-zA-Z0-9]{3,}")) {
            throw new NoValidDataException("Password");
        }
        this.password = password;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void printCompleteInformation() {
        System.out.printf("%n%s.%nNationalCode: %s%nBirthday: %s%nRole: %s%nArticles: %s%n",
                getUserName(), getNationalCode(), getBirthDay(), getRole()
                , getArticles().stream().map(Article::getTitle).collect(Collectors.joining(" & ")));
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getUserName(), getRole());
    }

    @Override
    public int compareTo(User u) {
        return this.getUserName().compareTo(u.getUserName());
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public User(String userName,String nationalCode){
        this.userName=userName;
        this.nationalCode=nationalCode;
    }


}
