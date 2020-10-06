package com.test.base.services.impl;

import com.test.base.domains.BaseEntity;
import com.test.base.repositories.BaseRepository;
import com.test.base.services.BaseService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<E extends BaseEntity<PK>, PK extends Serializable, Repository extends BaseRepository<E, PK>> implements BaseService<E, PK> {
    protected final Repository baseRepository;

    public BaseServiceImpl(Repository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public Optional<E> save(E e) {
        return baseRepository.save(e);
    }

    @Override
    public Optional<E> update(E e) {
        return baseRepository.update(e);
    }

    @Override
    public Optional<E> findById(Class<E> c, PK id) {
        return baseRepository.findById(c, id);
    }

    @Override
    public void delete(Class<E> c, PK id) {
        baseRepository.delete(c, id);
    }

    @Override
    public List<E> findAll(Class<E> c, String query) {
        return baseRepository.findAll(c, query);
    }
}
