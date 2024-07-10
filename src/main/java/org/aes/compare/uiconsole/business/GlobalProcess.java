package org.aes.compare.uiconsole.business;

import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;
import org.aes.compare.uiconsole.model.StaticData;
import org.aes.compare.uiconsole.utility.InputParserTree;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;
import java.util.Scanner;

public class GlobalProcess {
    private Scanner scanner = new Scanner(System.in);
    SafeScannerInput safeScannerInput = new SafeScannerInput();
    private InputParserTree inputParserTree = new InputParserTree();
    private List<String> list = StaticData.getGlobalProcessOptions();

    public void startProcess() {
        printListHowToProcess();
        selectGlobalProcess();
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
        EnumCMDLineParserResult result = inputParserTree.startProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
            runProcessIndexValue(input);
        }
        //inputParserTree.inputParse();
    }

    public void runProcessIndexValue(String index) {
        int val = safeScannerInput.convertInputToListIndexValue(index, list);
        if (val != -1) {
            System.out.println("Secilen Index degeri  : " + val + "> Daha sonrasinda " + list.get(val) + "'a gidicek");
//            HibernateImplementation.setHibernateConfigFile(EnumHibernateConfigFile.REAL_PRODUCT);

        }else{
            System.out.println("Tekrardan index alincak");
//            JpaImplementation.setPersistanceUnit(EnumJPAConfigFile.REAL_PRODUCT);
        }
    }
}
