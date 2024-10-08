package org.ahmeteminsaglik.uiconsole.business;

import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.ORMApp;
import org.ahmeteminsaglik.orm.consoleapplication.utility.FacadeUtility;
import org.ahmeteminsaglik.orm.model.Address;

import java.util.List;

public class UIConsoleDBServiceImplAddress {
    private final ORMApp ormApp = new ORMApp();

    private void addLoggerData() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_INNER_TERMINAL_PROCESS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
    }
    private  void destroyTerminalProcessSuccessfully(){
        FacadeUtility.destroyProcessSuccessfully(3);
    }
    public void create() {
        addLoggerData();
        Address address = ormApp.getAddressFacade().save();
        destroyTerminalProcessSuccessfully();
//        return address;
    }

    public void read() {
        addLoggerData();
        List<Address> addresses = ormApp.getAddressFacade().findAll();
        destroyTerminalProcessSuccessfully();
//        return addresses;
    }

    public void update() {
        addLoggerData();
        Address address = ormApp.getAddressFacade().updateAddressProcess();
        destroyTerminalProcessSuccessfully();
//        return address;
    }

    public void delete() {
        addLoggerData();
        ormApp.getAddressFacade().delete();
        destroyTerminalProcessSuccessfully();
    }

}
