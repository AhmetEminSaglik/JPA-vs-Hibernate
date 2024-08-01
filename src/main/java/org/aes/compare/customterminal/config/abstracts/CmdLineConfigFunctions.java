package org.aes.compare.customterminal.config.abstracts;

public interface CmdLineConfigFunctions {
    void enableCustomTerminalWithoutPrefix();

    void disableCustomTerminalWithoutPrefix();

    void updateOneLineCommandPrefix(String prefix);

//    boolean isActivated();

    String getPrefix();

    void printHelpInfoForCustomTerminal();
}
