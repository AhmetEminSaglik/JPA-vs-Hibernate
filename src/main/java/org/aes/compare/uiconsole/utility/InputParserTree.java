package org.aes.compare.uiconsole.utility;

import org.aes.compare.customterminal.business.abstracts.ProcessCommandService;
import org.aes.compare.customterminal.business.concretes.ProcessCommandServiceImpl;
import org.aes.compare.customterminal.config.abstracts.CmdLineConfigFunctions;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public class InputParserTree {
    CmdLineConfigFunctions cmdLine = CMDLineSingletonBuilder.getCmdLine();
    ProcessCommandService processCommandService = new ProcessCommandServiceImpl();

    public EnumCMDLineParserResult startProcess(String input) {
        if (cmdLine.isActivated() || input.startsWith(cmdLine.getPrefix())) {
            return runCMDLine(input);
        }
        return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }

    public EnumCMDLineParserResult runCMDLine(String input) {
//        System.out.println(">> komutlar ayrilcak ve islem yapilacak bulamazsa runStep'e gecicek");
//        cmdLine.getHelpForCustomTerminal();
//        try {
            return processCommandService.processCommand(input);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return false;
    }

}
