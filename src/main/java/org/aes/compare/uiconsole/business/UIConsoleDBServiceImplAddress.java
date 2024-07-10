package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;

import java.util.List;

public class UIConsoleDBServiceImplAddress implements AddressService {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    @Override
    public void save(Address address) {
        ormConfig.getAddressService().save(address);
    }

    @Override
    public Address findById(int id) {
        return ormConfig.getAddressService().findById(id);
    }

    @Override
    public List<Address> findAll() {
        return ormConfig.getAddressService().findAll();
    }

    @Override
    public void update(Address address) {
        ormConfig.getAddressService().update(address);
    }

    @Override
    public void deleteById(int id) {
        ormConfig.getAddressService().deleteById(id);
    }

    @Override
    public void resetTable() {
        ormConfig.getAddressService().resetTable();
    }
}
