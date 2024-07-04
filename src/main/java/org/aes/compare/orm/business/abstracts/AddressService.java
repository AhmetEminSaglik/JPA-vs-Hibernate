package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.model.Address;

public interface AddressService {
    void save(Address address);

    void update(Address address);

    void deleteById(int id);

    Address findById(int id);

    void resetTable();


}
