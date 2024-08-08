package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandProcessCheck;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.ORMApp;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Address;

import java.util.List;

public class UIConsoleDBServiceDisplayAddressMenu  {
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
