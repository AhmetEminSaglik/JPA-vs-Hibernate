package org.aes.compare.customterminal.business.abstracts;

import org.aes.compare.customterminal.model.TerminalCMD;

public interface RunnableTerminalCommand {
    void runCustomCommand(TerminalCommandLayout layout, TerminalCMD cmd);
}
