package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

public class UIConsoleDBServiceDisplayAddressMenu  {
    private final TerminalCommandManager tcm;

    public UIConsoleDBServiceDisplayAddressMenu(TerminalCommandManager tcm) {
        this.tcm = tcm;
    }

    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    public Address save() {
        Address address = new Address();
        System.out.println(ColorfulTextDesign.getInfoColorText("--> Address Save process is initialized"));
        System.out.print(ColorfulTextDesign.getTextForUserFeedback("Type city (String):"));
        String stringInput = SafeScannerInput.getStringInput();
        address.setCity(stringInput);


        System.out.print(ColorfulTextDesign.getTextForUserFeedback("Type Street (String):"));
        stringInput = SafeScannerInput.getStringInput();
        address.setStreet(stringInput);

        ormConfig.getAddressService().save(address);

        System.out.println(ColorfulTextDesign.getSuccessColorText("Address is saved: " + address));

        return address;

//        ormConfig.getAddressService().save(address);
    }

    public void findById() {
//        return ormConfig.getAddressService().findById(id);
    }

    public void findAll() {
//        return ormConfig.getAddressService().findAll();
    }

    public void update() {
//        ormConfig.getAddressService().update(address);
    }

    public void delete() {
//        ormConfig.getAddressService().deleteById(id);
    }

    public void resetTable() {
//        ormConfig.getAddressService().resetTable();
    }
}
