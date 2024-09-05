package org.ahmeteminsaglik.customterminal.config.concrete;

import org.ahmeteminsaglik.customterminal.config.abstracts.CmdLineConfigFunctions;
import org.ahmeteminsaglik.uiconsole.model.StaticData;

import java.util.List;

public class CmdLineConfigFunctionsImpl implements CmdLineConfigFunctions {
    private String prefix = ">>";

    @Override
    public void updateOneLineCommandPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void printHelpInfoForCustomTerminal() {
        List<String> helpInfos = StaticData.getCustomTerminalHelpInfoData();
        for (int i = 0; i < helpInfos.size(); i++) {
            System.out.println(helpInfos.get(i));
        }
    }
}
