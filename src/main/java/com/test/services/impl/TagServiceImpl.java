package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.Tag;
import com.test.repositories.TagRepository;
import com.test.services.TagService;

import java.util.Optional;

public class TagServiceImpl extends BaseServiceImpl<Tag, Long, TagRepository> implements TagService {
    public TagServiceImpl(TagRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        return baseRepository.findByTitle(title);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }
}
