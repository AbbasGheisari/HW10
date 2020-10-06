package com.test.base.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<E, PK extends Serializable> {
    Optional<E> save(E e);

    Optional<E> update(E e);

    Optional<E> findById(Class<E> c, PK id);

    void delete(Class<E> c, PK id);

    List<E> findAll(Class<E> c, String query);
}
