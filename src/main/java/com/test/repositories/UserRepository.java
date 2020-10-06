package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);
    List<User> findAllExceptMe(String userName);
    void delete(Long id);

}
