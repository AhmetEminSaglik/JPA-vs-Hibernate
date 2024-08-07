package org.aes.compare.customterminal.config.abstracts;

public interface CmdLineConfigFunctions {
    void updateOneLineCommandPrefix(String prefix);

    String getPrefix();

    void printHelpInfoForCustomTerminal();
}
