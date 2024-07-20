package org.aes.compare.uiconsole.utility;

import org.aes.compare.customterminal.business.concretes.ProcessCommandServiceImpl;
import org.aes.compare.customterminal.config.abstracts.CmdLineConfigFunctions;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public class InputParserTree {
    private TerminalCMD terminalCMD;// = new TerminalCMD();
    private final CmdLineConfigFunctions cmdLine = CMDLineSingletonBuilder.getCmdLine();
    private final ProcessCommandServiceImpl processCommand = new ProcessCommandServiceImpl();

    public EnumCMDLineParserResult decideProcess(String input) {
        if (cmdLine.isActivated() || input.startsWith(cmdLine.getPrefix())) {
            return runCMDLine(input);
        }
        return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }

    private EnumCMDLineParserResult runCMDLine(String input) {
        EnumCMDLineParserResult result = processCommand.processCommand(input);
        if (result == EnumCMDLineParserResult.RUN_FOR_CMDLINE) {
            this.terminalCMD = processCommand.getDecidedTerminalCMD();
        }
        return result;
    }

    public TerminalCMD getTerminalCMD() {
        return terminalCMD;
    }
}
