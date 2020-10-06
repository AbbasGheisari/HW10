package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.Role;
import com.test.repositories.RoleRepository;
import com.test.services.RoleService;

import java.util.Optional;

public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository> implements RoleService {

    public RoleServiceImpl(RoleRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Role> findByTitle(String title) {
        return baseRepository.findByTitle(title);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }
}
