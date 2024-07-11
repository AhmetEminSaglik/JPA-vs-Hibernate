package org.aes.compare.customterminal.config.concrete;

import org.aes.compare.customterminal.config.abstracts.CmdLineConfigFunctions;
import org.aes.compare.uiconsole.model.StaticData;

import java.util.List;

public class CmdLineConfigFunctionsImpl implements CmdLineConfigFunctions {

    private boolean isActivated = false;
    private String prefix = ">>";

    @Override
    public void enableCustomTerminalWithoutPrefix() {
        isActivated = true;
    }

    @Override
    public void disableCustomTerminalWithoutPrefix() {
        isActivated = false;
    }

    @Override
    public void updateOneLineCommandPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean isActivated() {
        return isActivated;
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
