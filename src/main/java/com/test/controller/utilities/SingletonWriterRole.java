package com.test.controller.utilities;

import com.test.domains.Role;
import com.test.services.RoleService;

import java.util.Optional;

public final class SingletonWriterRole {

    private SingletonWriterRole() {
    }

    public static synchronized Role getWriterRole(RoleService roleService) {
        Optional<Role> oRole = roleService.findByTitle("writer");
        if (oRole.isPresent())
            return oRole.get();
        else {
            Role role = new Role("writer");
            return role;
        }
    }
}
