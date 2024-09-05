package org.ahmeteminsaglik.uiconsole.business;

import org.ahmeteminsaglik.customterminal.business.abstracts.TerminalCommandLayout;
import org.ahmeteminsaglik.customterminal.business.concretes.TerminalCommandManager;
import org.ahmeteminsaglik.customterminal.model.TerminalCMD;
import org.ahmeteminsaglik.orm.config.ORMConfigSingleton;
import org.ahmeteminsaglik.uiconsole.model.EnumCMDLineParserResult;
import org.ahmeteminsaglik.uiconsole.utility.InputParserTree;

import java.util.Scanner;

public class GlobalProcess extends TerminalCommandLayout {
    private final Scanner scanner = new Scanner(System.in);
    private final InputParserTree inputParserTree = new InputParserTree();

    public void startProcess() {

        ORMConfigSingleton.enableJPA();
        while (isAllowedCurrentProcess) {
            System.out.println("UIConsoleApp > 1.While");
            selectGlobalProcess();
        }

    }

    public void selectGlobalProcess() {
        String input = scanner.nextLine();
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(this, terminalCMD);
        }
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
        }
    }

}
