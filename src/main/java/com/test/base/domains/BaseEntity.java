package com.test.base.domains;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<PK extends Serializable> implements Serializable {
    @Id
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
