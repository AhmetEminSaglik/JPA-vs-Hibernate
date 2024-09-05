package org.ahmeteminsaglik.orm.business.abstracts;

import org.ahmeteminsaglik.orm.model.Address;

import java.util.List;

public interface AddressService {

    void save(Address address);

    Address findById(int id);

    List<Address> findAll();

    List<Address> findAllSavedAndNotMatchedAnyStudentAddress();

    void update(Address address);

    void deleteById(int id);

    void resetTable();

}
