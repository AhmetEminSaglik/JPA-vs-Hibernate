package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandProcessCheck;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public class UIConsoleDBServiceDisplayAddressMenu implements TerminalCommandProcessCheck {
    private final TerminalCommandManager tcm;
    private final TerminalCommandLayout layout;
    private final ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    public UIConsoleDBServiceDisplayAddressMenu(TerminalCommandManager tcm, TerminalCommandLayout layout) {
        this.tcm = tcm;
        this.layout = layout;
    }

    public Address save() {
        System.out.println(ColorfulTextDesign.getInfoColorText("UI CONSOLE save  Address"));
        return null;
    }

    public void findById() {
        System.out.println(ColorfulTextDesign.getInfoColorText("UI CONSOLE findById  Address"));
//        return ormConfig.getAddressService().findById(id);
    }

    public void findAll() {
        System.out.println(ColorfulTextDesign.getInfoColorText("UI CONSOLE findAll  Address"));
//        return ormConfig.getAddressService().findAll();
    }

    public void update() {
        System.out.println(ColorfulTextDesign.getInfoColorText("UI CONSOLE update  Address"));
//        ormConfig.getAddressService().update(address);
    }

    public void delete() {
        System.out.println(ColorfulTextDesign.getInfoColorText("UI CONSOLE delete  Address"));
//        ormConfig.getAddressService().deleteById(id);
    }

    public void resetTable() {
//        ormConfig.getAddressService().resetTable();
    }

    @Override
    public boolean isCanceled() {
        return tcm.isAllowedCurrentProcess();
    }
}
