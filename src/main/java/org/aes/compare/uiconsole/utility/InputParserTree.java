package org.ahmeteminsaglik.uiconsole.utility;

import org.ahmeteminsaglik.customterminal.business.concretes.ProcessCommandServiceImpl;
import org.ahmeteminsaglik.customterminal.config.abstracts.CmdLineConfigFunctions;
import org.ahmeteminsaglik.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.ahmeteminsaglik.customterminal.model.TerminalCMD;
import org.ahmeteminsaglik.uiconsole.model.EnumCMDLineParserResult;

public class InputParserTree {
    private final CmdLineConfigFunctions cmdLine = CMDLineSingletonBuilder.getCmdLine();
    private final ProcessCommandServiceImpl processCommand = new ProcessCommandServiceImpl();
    private TerminalCMD terminalCMD;// = new TerminalCMD();

    public EnumCMDLineParserResult decideProcess(String input) {
        if (input.startsWith(cmdLine.getPrefix())) {
            return runCMDLine(input);
        }
        return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }

    private EnumCMDLineParserResult runCMDLine(String input) {
        EnumCMDLineParserResult result = processCommand.processCommand(input);
        if (result != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE) {
            this.terminalCMD = processCommand.getDecidedTerminalCMD();
        }
        // todo cancel kisminda kaldim.
        return result;
    }

    public TerminalCMD getTerminalCMD() {
        return terminalCMD;
    }
}
