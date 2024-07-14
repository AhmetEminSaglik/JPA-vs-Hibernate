package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.ProcessCommandServiceImpl;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;
import org.aes.compare.uiconsole.model.StaticData;
import org.aes.compare.uiconsole.utility.InputParserTree;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;
import java.util.Scanner;

public class GlobalProcess extends TerminalCommandLayout {
    private Scanner scanner = new Scanner(System.in);
    SafeScannerInput safeScannerInput = new SafeScannerInput();
    private InputParserTree inputParserTree = new InputParserTree();
    private List<String> list = StaticData.getGlobalProcessOptions();

    public void startProcess() {

        ORMConfigSingleton.enableJPA();
        while (true && isAllowedCurrentProcess) {
            System.out.println("UIConsoleApp > 1.While");
            printListHowToProcess();
            selectGlobalProcess();
        }

    }

/*
    private void processGlobalList() {
        while (true) {
            printListHowToProcess();
            selectGlobalProcess();
        }
    }
*/

    private void printListHowToProcess() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
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
            runProcessIndexValue(input);
        }
    }

    public void runProcessIndexValue(String index) {
        int val = safeScannerInput.convertInputToListIndexValue(index, list);
        String text;
        if (val != -1) {
            text = "Secilen Index degeri  : " + val + "> Daha sonrasinda " + list.get(val) + "'a gidicek";
            System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(text));
//            HibernateImplementation.setHibernateConfigFile(EnumHibernateConfigFile.REAL_PRODUCT);

        }else{
            text = "Tekrardan index alincak";
            System.out.println(ColorfulTextDesign.getErrorColorText(text));
//            JpaImplementation.setPersistanceUnit(EnumJPAConfigFile.REAL_PRODUCT);
        }
    }
}
