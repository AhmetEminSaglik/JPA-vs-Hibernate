package org.aes.compare.orm.business.concrete.hibernate;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.aes.compare.orm.model.Address;

import java.util.List;

public class AddressServiceImplHibernate extends HibernateImplementation<Address> implements AddressService {

    @Override
    public void save(Address address) {
        initializeTransaction();
        session.persist(address);
        commit();
    }

    @Override
    public Address findById(int id) {
        initializeTransaction();
        Address address = session.find(Address.class, id);
        commit();
        return address;
    }

    @Override
    public List<Address> findAll() {
        initializeTransaction();
        TypedQuery<Address> query = session.createQuery("SELECT a FROM Address a ", Address.class);
        List<Address> addresses = query.getResultList();
        commit();
        return addresses;
    }

    @Override
    public List<Address> findAllSavedAndNotMatchedAnyStudentAddress() {
        initializeTransaction();
        TypedQuery<Address> query = session.createQuery("SELECT a FROM Address a " +
                "LEFT JOIN a.student s WHERE s.id IS NULL", Address.class);
        List<Address> addresses = query.getResultList();
        commit();
        return addresses;
    }

    @Override
    public void update(Address address) {
        initializeTransaction();
        session.merge(address);
        commit();
    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        Address address = session.find(Address.class, id);
        session.remove(address);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();
        session.createNativeMutationQuery("DELETE FROM address")
                .executeUpdate();
        commit();

        initializeTransaction();

        session.createNativeMutationQuery("ALTER TABLE address AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();

    }

}
