/*
package org.aes.compare.customterminal.config.abstracts;

import org.aes.compare.uiconsole.model.StaticData;

import java.util.List;

public abstract class AbstractCMDLineConfiguration {
    protected boolean isActivated = false;
    protected String prefix = ">>";
    private List<String> helpInfos = StaticData.getCustomTerminalHelpInfoData();

    public abstract void enableCustomTerminalWithoutPrefix();

    public abstract void disableCustomTerminalWithoutPrefix();

    public abstract void updateOneLineCommandPrefix(String prefix);

    public boolean isActivated() {
        return isActivated;
    }

    public String getPrefix() {
        return prefix;
    }

    protected void getHelpForCustomTerminal() {
        for (int i = 0; i < helpInfos.size(); i++) {
            System.out.println(helpInfos.get(i));
        }
    }
}
*/
