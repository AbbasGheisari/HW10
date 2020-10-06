package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.Tag;

import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Optional<Tag> findByTitle(String title);

    void delete(Long id);
}
