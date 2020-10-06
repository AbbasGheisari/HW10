package com.test.domains;

import com.test.base.domains.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sada")
@NamedQueries({@NamedQuery(name = "SampleTest.findAll",query = "select s from SampleTest s")})


public class SampleTest extends BaseEntity<Long> {

    @Transient
    private static long count = 1;

    @Column(nullable = false)
    private String value1;

    @Column(nullable = false)
    private String value2;

    @Column(nullable = false)
    private String value3;

    public SampleTest(){
        this.setId(count);
        count++;
    }


    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public void printCompleteInformation() {
        System.out.printf(" value1 : %s%n value2 : %s%n value3 : %s%n," ,
                getValue1(), getValue2(), getValue3()
        );
    }
}
