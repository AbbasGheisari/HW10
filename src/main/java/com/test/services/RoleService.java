package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.Role;

import java.util.Optional;

public interface RoleService extends BaseService<Role,Long> {

    Optional<Role> findByTitle(String title);

    void delete(Long id);
}
