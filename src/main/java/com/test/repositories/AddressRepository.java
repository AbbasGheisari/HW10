package com.test.repositories;

import com.test.base.repositories.BaseRepository;
import com.test.domains.Address;

import java.util.Optional;

public interface AddressRepository extends BaseRepository<Address,Long> {
    Optional<Address> findByUserName(String userName);
}



