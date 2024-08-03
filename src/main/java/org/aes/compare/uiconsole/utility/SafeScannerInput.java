package org.aes.compare.uiconsole.utility;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

import java.text.DecimalFormat;
import java.util.Scanner;

public class SafeScannerInput {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputParserTree inputParserTree = new InputParserTree();

    public static Integer getCertainIntSafe(TerminalCommandLayout terminalLayout) {
        try {

//            int num = Integer.parseInt(scanner.nextLine().trim());
            System.out.println(" Burda deger alinmali. terminal ");
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);

            if (enumResult.getId() == EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId()) {
                System.out.println("----------> Terminal islemi yapildi. bitiyor.");
                return EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId();
            }

            int num = Integer.parseInt(inputText);

            return num;
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Integer Input. Please try again."));
            return getCertainIntSafe(terminalLayout);
        }
    }

    public static Integer getCertainIntSafe(TerminalCommandLayout terminalLayout, int min, int max) {
//        try {

        int num = getCertainIntSafe(terminalLayout);
        if (num == EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId()) {
            System.out.println("CMD_CANCEL_PROCESS ----------> Terminal islemi yapildi. bitiyor.");
            return EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId();
        }if (num == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            System.out.println("RUN_FOR_CMDLINE----------> Terminal islemi yapildi. bitiyor .");
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId();
        }
        if (num >= min && num <= max) {
            return num;
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Number Range Value. Please type number between [" + min + "-" + max + "]"));
            return getCertainIntSafe(terminalLayout, min, max);
        }
        /*} catch (NumberFormatException ex) {
            System.out.println("Invalid Index Input. Please try again.");
//            return getCertainIntSafe(min,max);
        }*/
    }

    /*public static Double getCertainDoubleSafe() {
        try {
            double num = Double.parseDouble(scanner.nextLine().trim());
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.parseDouble(df.format(num));
//            return num;
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Double value Input. Please try again. (Example : 12.34)"));
            return getCertainDoubleSafe();
        }
    }*/
    private static EnumCMDLineParserResult selectTerminalProcess(TerminalCommandLayout terminalLayout, String input) {
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(terminalLayout, terminalCMD);
            System.out.println("selectTerminalProcess > CMD_CANCEL_PROCESS "+terminalLayout.isAllowedCurrentProcess());
            return EnumCMDLineParserResult.CMD_CANCEL_PROCESS;
        } else if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            System.out.println("selectTerminalProcess > RUN_FOR_CMDLINE "+terminalLayout.isAllowedCurrentProcess());
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(terminalLayout, terminalCMD);
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
//        }
        }
        return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
    }

    public static int getCertainIntForSwitch(TerminalCommandLayout terminalLayout, String text, int minRange, int maxRange) {
        System.out.print(text);

//        String errMsg = "Type number between :[" + minRange + "-" + maxRange + "]";
//        String errMsg = "Type number between :[" + minRange + "-" + maxRange + "]";
        String errMsg = (ColorfulTextDesign.getErrorColorText("Please Type number between [" + minRange + "," + maxRange + "]"));
        try {
            System.out.println(" Burda deger alinmali. terminal ");
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);
            /*if (enumResult.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
                System.out.println("----------> Terminal islemi yapildi. bitiyor.");
                return EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId();
            }*/
            System.out.println("RUN_FOR_INDEX_VALUE checkine geldi");
            if (enumResult.getId() != EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
                System.out.println("Burdan donucek");
                return enumResult.getId();
            }
            int val = Integer.parseInt(inputText);
//            scanner.nextLine();
            if (val >= minRange && val <= maxRange) {
                return val;
            }
            errMsg = "Invalid number (" + val + ") . " + errMsg;
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(errMsg));
        } catch (NumberFormatException e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Please Type number between [" + minRange + "," + maxRange + "]"));
        }
        return getCertainIntForSwitch(terminalLayout, text, minRange, maxRange);
    }

    public static Double getCertainDoubleSafe(TerminalCommandLayout terminalLayout, int min, int max) {
//        try {
        double num;
        try {
            System.out.println(" Burda deger alinmali. terminal ");
            String inputText = scanner.nextLine().trim();
            EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, inputText);
            if (enumResult.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
                System.out.println("----------> Terminal islemi yapildi. bitiyor.");
                return (double) EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId();
            }

            num = Double.parseDouble(inputText);
            DecimalFormat df = new DecimalFormat("#.00");
//            return Double.parseDouble(df.format(num));
            num = Double.parseDouble(df.format(num));
//            return num;
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Double value Input. Please try again. (Example : 12.34)"));
            return getCertainDoubleSafe(terminalLayout, min, max);
        }


//        double num = getCertainDoubleSafe();
        if (num >= min && num <= max) {
            return num;
        } else {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Error Occurred: Invalid Number Range Value. Please type number between [" + min + "-" + max + "]"));
            return getCertainDoubleSafe(terminalLayout, min, max);
        }
        /*} catch (NumberFormatException ex) {
            System.out.println("Invalid Index Input. Please try again.");
//            return getCertainIntSafe(min,max);
        }*/
    }

    public static String getStringNotBlank(TerminalCommandLayout terminalLayout) {
        String text = scanner.nextLine().trim();
        EnumCMDLineParserResult enumResult = selectTerminalProcess(terminalLayout, text);
//        System.out.println();
        if (enumResult.getId() == EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId()) {
            System.out.println("----------> Terminal islemi yapildi. CMD_CANCEL_PROCESS.");
            return Double.toString(EnumCMDLineParserResult.CMD_CANCEL_PROCESS.getId());
        }
        if (enumResult.getId() == EnumCMDLineParserResult.CMD_PROCESS_COMPLETED.getId()) {
            System.out.println("----------> Terminal islemi yapildi. CMD_PROCESS_COMPLETED.");
            return Double.toString(EnumCMDLineParserResult.CMD_PROCESS_COMPLETED.getId());
        }
        
        if (text.isBlank()) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Empty String is not allowed. Please type something."));
            return getStringNotBlank(terminalLayout);
        }
        return text;
    }

    /*public static Integer getInt(String input) {
        try {
            int num = Integer.parseInt(input);
            return num;
        } catch (NumberFormatException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Index Input. Please try again."));
            return null;
        }
    }*/

    /*public static String getStringInput(String inputMsg, TerminalCommandLayout tmc) {
        System.out.println(inputMsg);
        String input = scanner.nextLine().trim();
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(tmc, terminalCMD);
        }
        if (!tmc.isAllowedCurrentProcess()) {
            return "";
        }
        if (input.contains(CMDLineSingletonBuilder.getCmdLine().getPrefix())) {
            return getStringInput(inputMsg, tmc);
        }
        if (input.trim().isEmpty()) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Blank is not allowed. Please type something"));
            return getStringInput(inputMsg, tmc);
        }
        return input;
    }*/

    /*public static int getIntInput(int minRange, int maxRange, String inputMsg, TerminalCommandLayout tmc) {
        System.out.println(inputMsg);
        String input = scanner.nextLine().trim();
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);


        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(tmc, terminalCMD);
        }

        Integer num = getInt(input);
        if (!tmc.isAllowedCurrentProcess()) {
            return -1;
        }
        if (input.contains(CMDLineSingletonBuilder.getCmdLine().getPrefix())) {
            return getIntInput(inputMsg, tmc);
        }
        if (num == null) {
            return getIntInput(inputMsg, tmc);
        }
        if (num < minRange || num > maxRange) {
            System.out.println("Number must be between " + minRange + "-" + maxRange + ".");
            return getIntInput(minRange, maxRange, inputMsg, tmc);
        }
        return num;
    }*/

    /*public static int getIntInput(String inputMsg, TerminalCommandLayout tmc) {
        System.out.println(inputMsg);
        String input = scanner.nextLine().trim();
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);


        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(tmc, terminalCMD);
        }

        Integer num = getInt(input);
        if (!tmc.isAllowedCurrentProcess()) {
            return -1;
        }
        if (input.contains(CMDLineSingletonBuilder.getCmdLine().getPrefix())) {
            return getIntInput(inputMsg, tmc);
        }
        if (num == null) {
            return getIntInput(inputMsg, tmc);
        }
        return num;
    }*/



    /*public static int getCertainIntForSwitch(int minRange, int maxRange) {
        String errMsg = "Type number between :[" + minRange + "-" + maxRange + "]";
        try {
            String inputText = scanner.nextLine().trim();
            int val = Integer.parseInt(inputText);
//            scanner.nextLine();
            if (val >= minRange && val <= maxRange) {
                return val;
            }
            errMsg = "Invalid number : " + val + "." + errMsg;
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(errMsg));
        } catch (NumberFormatException e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Please type only number between (" + minRange + "-" + maxRange + ")"));
        }
        return getCertainIntForSwitch(minRange, maxRange);
    }*/

    /*public int convertInputToListIndexValue(String input, List<?> list) {
        Integer integer = getInt(input);
        if (integer != null) {
            int num = --integer;
            boolean result = isNumberSuitableListRange(num, list);
            if (result) {
                return num;
            }
        }
        return -1;

    }*/

    /*private boolean isNumberSuitableListRange(int num, List<?> list) {
        if (num >= 0 && num < list.size()) {
            return true;
        }
        System.out.println("Invalid Index range. Please choose number between 0-" + (list.size() - 1));
        return false;
    }*/


}
