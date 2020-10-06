package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.Tag;

import java.util.Optional;

public interface TagService extends BaseService<Tag, Long> {

    Optional<Tag> findByTitle(String title);

    void delete(Long id);
}
