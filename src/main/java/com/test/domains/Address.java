package com.test.domains;


import com.test.base.domains.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "address")
@NamedQueries({
        @NamedQuery(name = "Address.findByUsername", query = "select ad from Address ad  where ad.user.userName=:userName")
})


public class Address extends BaseEntity<Long> {


    @Transient
    private static long count = 2;



    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String alley;
    @Column(nullable = false,unique = true)
    private Long postalCode;

    @OneToOne(mappedBy = "address")
    private User user;

    public Address(){
        this.setId(count);
        count++;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAlley() {
        return alley;
    }

    public void setAlley(String alley) {
        this.alley = alley;
    }

    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public void printCompleteInformation() {
        System.out.printf(" City : %s%n Street : %s%n Alley: %s%n PostalCode : %d%n",
                getCity(), getStreet(), getAlley(), getPostalCode()
                );
    }


}
