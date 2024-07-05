package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.model.Address;

import java.util.List;

public interface AddressService {

    void save(Address address);

    Address findById(int id);

    List<Address> findAll();

    void update(Address address);

    void deleteById(int id);

    void resetTable();


}
