package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.Query;
import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.model.Address;

import java.util.List;

public class AddressServiceImplJPA extends JpaImplementation<Address> implements AddressService {
    @Override
    public void save(Address address) {
        initializeTransaction();
        getEntityManager().persist(address);
        commit();
    }

    @Override
    public void update(Address address) {
        initializeTransaction();
        getEntityManager().merge(address);
        commit();

    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        Address address =getEntityManager().find(Address.class,id);
        getEntityManager().remove(address);
        commit();
    }

    @Override
    public void resetTable() {

        initializeTransaction();

        getEntityManager().createQuery("DELETE FROM Address")
                .executeUpdate();
        commit();

        initializeTransaction();

        getEntityManager().createNativeQuery("ALTER TABLE address AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();

    }

    @Override
    public Address findById(int id) {
        initializeTransaction();
        Address address = getEntityManager().find(Address.class, id);
        //if EntityManger is not closed, then pc goes to endless loop
        commit();
        return address;
    }

    @Override
    public List<Address> findAll() {
        initializeTransaction();
        // todo  test is there any difference between select from Address  vs select a from Adress a
        Query query = getEntityManager().createQuery("SELECT a FROM Address a ");
        List<Address> addresses = query.getResultList();
        commit();
        return addresses;
    }
}
