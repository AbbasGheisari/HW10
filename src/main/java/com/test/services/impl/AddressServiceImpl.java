package com.test.services.impl;

import com.test.base.services.impl.BaseServiceImpl;
import com.test.domains.Address;
import com.test.repositories.AddressRepository;
import com.test.services.AddressService;

import java.util.Optional;

public class AddressServiceImpl extends BaseServiceImpl<Address, Long, AddressRepository> implements AddressService {

    public AddressServiceImpl(AddressRepository baseRepository){
        super(baseRepository);
    }

    @Override
    public Optional<Address> findByUserName(String userName) {
        return baseRepository.findByUserName(userName);
    }

}
