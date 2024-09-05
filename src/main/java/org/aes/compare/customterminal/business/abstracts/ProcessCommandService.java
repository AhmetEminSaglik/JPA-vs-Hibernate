package org.ahmeteminsaglik.customterminal.business.abstracts;

import org.ahmeteminsaglik.customterminal.model.TerminalCMD;
import org.ahmeteminsaglik.uiconsole.model.EnumCMDLineParserResult;

public interface ProcessCommandService {

    EnumCMDLineParserResult processCommand(String text);

    EnumCMDLineParserResult parseCommand(String text) throws Exception;

    String clearCommand(String text);

    TerminalCMD getDecidedTerminalCMD();
}
