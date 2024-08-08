package org.aes.compare.uiconsole.utility;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

import java.text.DecimalFormat;
import java.util.Scanner;

public class SafeScannerInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputParserTree inputParserTree = new InputParserTree();

    private static EnumCMDLineParserResult selectTerminalProcess(TerminalCommandLayout terminalLayout, String input) {
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);

        if (result.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(terminalLayout, terminalCMD);
            return result;
        }
        return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }

    public static int getCertainIntForSwitch(TerminalCommandLayout terminalLayout, int minRange, int maxRange) {
        String errMsg = (ColorfulTextDesign.getErrorColorText("Please Type number between [" + minRange + "," + maxRange + "]"));
        try {
            System.out.print(MetaData.SELECT_ONE_OPTION);
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);

            if (enumResult.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
                return EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId();
            }
            int val = Integer.parseInt(inputText);
            if (val >= minRange && val <= maxRange) {
                return val;
            }
            errMsg = "Invalid number (" + val + ") . " + errMsg;
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(errMsg));
        } catch (NumberFormatException e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Please Type number between [" + minRange + "," + maxRange + "]"));
        }
        return getCertainIntForSwitch(terminalLayout, minRange, maxRange);
    }

    public static Double getCertainDoubleSafe(TerminalCommandLayout terminalLayout,String title, int min, int max) {
        System.out.print(title);
        double num;
        try {
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);

            if (enumResult.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
                return (double) EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId();
            }
            num = Double.parseDouble(inputText);

            DecimalFormat df = new DecimalFormat("#.00");
            num = Double.parseDouble(df.format(num));
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Double value Input. Please try again. (Example : 12.34)"));
            return getCertainDoubleSafe(terminalLayout,title, min, max);
        }
        if (num >= min && num <= max) {
            return num;
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Error Occurred: Invalid Number Range Value. Please type number between [" + min + "-" + max + "]"));
            return getCertainDoubleSafe(terminalLayout,title, min, max);
        }
    }

    public static String getStringNotBlank(TerminalCommandLayout terminalLayout,String title) {
        System.out.print(title);
        String text = scanner.nextLine().trim();
        EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, text);
        if (enumResult.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
            return Integer.toString(EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId());
        }
        if (text.isBlank()) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Empty String is not allowed. Please type something."));
            return getStringNotBlank(terminalLayout,title);
        }
        return text;
    }


    public static Integer getCertainIntSafe(TerminalCommandLayout terminalLayout,String title) {
        try {
            System.out.print(title);
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);
            if (enumResult.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
                return EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId();
            }
            int num = Integer.parseInt(inputText);
            return num;
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Integer Input. Please try again."));
            return getCertainIntSafe(terminalLayout,title);
        }
    }

    public static Integer getCertainIntSafe(TerminalCommandLayout terminalLayout,String title, int min, int max) {
        int num = getCertainIntSafe(terminalLayout,title);

        if (num == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId();
        }
        if (num >= min && num <= max) {
            return num;
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Number Range Value. Please type number between [" + min + "-" + max + "]"));
            return getCertainIntSafe(terminalLayout,title, min, max);
        }
    }

    public static Integer getCertainIntSafe(int min, int max) {

        try {
            String text = scanner.nextLine();
            int num = Integer.parseInt(text);
            if (num >= min && num <= max) {
                return num;
            } else {
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Number Range Value. Please type number between [" + min + "-" + max + "]"));
                return getCertainIntSafe(min, max);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Integer Input. Please try again."));
            return getCertainIntSafe(min, max);
        }

    }
}
