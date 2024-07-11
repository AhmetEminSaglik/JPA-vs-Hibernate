package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.customterminal.business.abstracts.RunnableTerminalCommand;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.TerminalCMD;

public class TerminalCommandManager implements RunnableTerminalCommand {
    @Override
    public void runCustomCommand(TerminalCommandLayout layout, TerminalCMD cmd) {
        if (cmd.getStandardCommand() != null) {
            switch (cmd.getStandardCommand()) {
                case HELP:
                    printHelpInfo();
                    break;
                case QUIT_PROCESS:
                    layout.quitCurrentProcess();
                    break;
                case QUIT_PROGRAM:
                    System.exit(0);
                    break;
            }

        }
    }

    public void printHelpInfo() {
        CMDLineSingletonBuilder.getCmdLine().printHelpInfoForCustomTerminal();
    }

}
