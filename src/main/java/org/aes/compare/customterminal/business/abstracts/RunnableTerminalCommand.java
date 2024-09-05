package org.ahmeteminsaglik.customterminal.business.abstracts;

import org.ahmeteminsaglik.customterminal.model.TerminalCMD;

public interface RunnableTerminalCommand {
    void runCustomCommand(TerminalCommandLayout layout, TerminalCMD cmd);
}
