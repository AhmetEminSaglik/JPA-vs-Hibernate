package org.aes.compare.orm.utility;

import org.aes.compare.metadata.MetaData;
import org.ahmeteminsaglik.config.PrintConsoleServiceConfig;
import org.ahmeteminsaglik.config.PrintableConsoleServiceManager;
import org.ahmeteminsaglik.printable.EnumPrintOption;
import org.ahmeteminsaglik.printable.abstracts.PrintableConsoleService;

public class ColorfulTextDesign {
    private static PrintableConsoleService ps = new PrintableConsoleServiceManager();
    private static String currentSelectedPrintObject = "";

    public static String getCurrentSelectedPrintObjectName() {
        return currentSelectedPrintObject;
    }

    public static void enableCMDPrinting() {
        PrintConsoleServiceConfig.updatePrintableService(EnumPrintOption.WINDOWS_CMD);
        ps = PrintConsoleServiceConfig.getPrintableService();
        currentSelectedPrintObject=EnumPrintOption.WINDOWS_CMD.getName();
        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_RESULT_PREFIX) + "Activated Printing Tool : " + ColorfulTextDesign.getSuccessColorText(ColorfulTextDesign.getCurrentSelectedPrintObjectName()));
    }

    public static void enableIDEPrinting() {
        PrintConsoleServiceConfig.updatePrintableService(EnumPrintOption.JAVA_IDE);
        ps = PrintConsoleServiceConfig.getPrintableService();
        currentSelectedPrintObject=EnumPrintOption.JAVA_IDE.getName();
        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_RESULT_PREFIX) + "Activated Printing Tool : " + ColorfulTextDesign.getSuccessColorText(ColorfulTextDesign.getCurrentSelectedPrintObjectName()));
    }

    public static void enableStandardPrinting() {
        PrintConsoleServiceConfig.updatePrintableService(EnumPrintOption.STANDARD);
        ps = PrintConsoleServiceConfig.getPrintableService();
        currentSelectedPrintObject=EnumPrintOption.STANDARD.getName();
        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_RESULT_PREFIX) + "Activated Printing Tool : " + ColorfulTextDesign.getSuccessColorText(ColorfulTextDesign.getCurrentSelectedPrintObjectName()));
    }

    public static String getText(String color, String msg) {
        return ps.getColorfulText(color + msg);
    }

    public static String getInfoColorTextWithPrefix(String msg) {
        return ps.getInfoColor("[INFO]: " + msg);
    }

    public static String getInfoColorText(String msg) {
        return ps.getInfoColor(msg);
    }


    public static String getErrorColorTextWithPrefix(String msg) {
        return ps.getErrorColor("[ERROR]: " + msg);
    }

    public static String getErrorColorText(String msg) {
        return ps.getErrorColor(msg);
    }


    public static String getTextForCanceledProcess(String msg) {
        return ps.getCancelColor(msg);
    }


    public static String getTextForUserFeedback(String msg) {
        return ps.getColorfulText(msg);
    }

    public static String getSuccessColorTextWithPrefix(String msg) {
        return ps.getSuccessColor("[SUCCESS]: " + msg);
    }

    public static String getSuccessColorText(String msg) {
        return ps.getSuccessColor(msg);
    }


    public static String getWarningColorTextWithPrefix(String msg) {
        return ps.getWarningColor("[WARNING]: " + msg);
    }

    public static String getWarningColorText(String msg) {
        return ps.getWarningColor(msg);
    }

}
