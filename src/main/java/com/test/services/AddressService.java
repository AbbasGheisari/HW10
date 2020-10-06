
package com.test.services;

import com.test.base.services.BaseService;
import com.test.domains.Address;

import java.util.Optional;

public interface AddressService extends BaseService<Address, Long> {

    Optional<Address> findByUserName(String userName);

}
