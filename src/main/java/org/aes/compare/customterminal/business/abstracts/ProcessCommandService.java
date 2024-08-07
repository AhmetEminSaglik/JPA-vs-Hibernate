package org.aes.compare.customterminal.business.abstracts;

import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public interface ProcessCommandService {

    EnumCMDLineParserResult processCommand(String text);

    EnumCMDLineParserResult parseCommand(String text) throws Exception;

    String clearCommand(String text);

    TerminalCMD getDecidedTerminalCMD();
}
