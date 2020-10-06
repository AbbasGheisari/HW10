package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Long> {
    Optional<User> findUserByUserName(String userName);

    List<User> findAllExceptMe(String userName);

    void delete(Long id);
}
