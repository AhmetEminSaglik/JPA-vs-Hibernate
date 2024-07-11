package org.aes.compare.uiconsole.utility;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

import java.util.List;
import java.util.Scanner;

public class SafeScannerInput {
    private static Scanner scanner = new Scanner(System.in);
    private static InputParserTree inputParserTree = new InputParserTree();

    public int convertInputToListIndexValue(String input, List<?> list) {
        Integer integer = getInt(input);
        if (integer != null) {
            int num = --integer;
            boolean result = isNumberSuitableListRange(num, list);
            if (result) {
                return num;
            }
        }
        return -1;

    }

    public static Integer getInt(String input) {
        try {
            int num = Integer.parseInt(input);
            return num;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Index Input. Please try again.");
            return null;
        }
    }

    private boolean isNumberSuitableListRange(int num, List<?> list) {
        if (num >= 0 && num < list.size()) {
            return true;
        }
        System.out.println("Invalid Index range. Please choose number between 0-" + (list.size() - 1));
        return false;
    }

    public static String getStringInput(TerminalCommandLayout tmc) {
        String input = scanner.nextLine();


        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(tmc, terminalCMD);
        }

        if (input.trim().isEmpty()) {
            System.out.println(ColorfulTextDesign.getErrorColorText("Blank is not allowed. Please type something"));
            return getStringInput(tmc);
        }
        return input;
    }

    public static int getIntInput(TerminalCommandLayout tmc) {
        String input = scanner.nextLine();

        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(tmc, terminalCMD);
        }

        Integer num = getInt(input);
        if (tmc.isCurrentProcessCanceled()) {
            return -1;
        }
        if (num == null) {
            return getIntInput(tmc);
        }
        return num;
    }

}
