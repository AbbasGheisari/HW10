package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.Role;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByTitle(String title);

    void delete(Long id);
}
