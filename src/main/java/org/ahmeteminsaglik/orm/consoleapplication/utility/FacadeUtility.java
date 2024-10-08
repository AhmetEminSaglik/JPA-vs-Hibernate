package org.ahmeteminsaglik.orm.consoleapplication.utility;

import org.ahmeteminsaglik.customterminal.business.abstracts.TerminalCommandLayout;
import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;
import org.ahmeteminsaglik.uiconsole.business.LoggerProcessStack;
import org.ahmeteminsaglik.uiconsole.model.EnumCMDLineParserResult;
import org.ahmeteminsaglik.uiconsole.utility.SafeScannerInput;

import java.util.List;

public class FacadeUtility {

    public static boolean isCancelledProcess(TerminalCommandLayout terminalLayout) {
        return !terminalLayout.isAllowedCurrentProcess();
    }

    public static int getIndexValueOfMsgListIncludesExit(TerminalCommandLayout terminalCommandLayout, List<?> list) {
//        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
//        msg.append('(').append(0).append(") Exit\n");
//        return getUserIndexInputOfOptionList(terminalCommandLayout, msg, list, 0);
        return getIndexValueOfMsgListIncludesExitWitchCustomSelectionOptionMessage(terminalCommandLayout, MetaData.AVAILABLE_OPTIONS, list);
    }

    public static int getIndexValueOfMsgListIncludesExitWitchCustomSelectionOptionMessage(TerminalCommandLayout terminalCommandLayout, String selectionOptionMsg, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + selectionOptionMsg);
        msg.append('(').append(0).append(") Exit\n");
        return getUserIndexInputOfOptionList(terminalCommandLayout, msg, list, 0);
    }

    private static int getIndexValueOfMsgListIncludesCancelAndSaveExits(TerminalCommandLayout terminalCommandLayout, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
        msg.append('(').append(-1).append(") Cancel & Exit\n");
        msg.append('(').append(0).append(") Save & Exit\n");
        return getUserIndexInputOfOptionList(terminalCommandLayout, msg, list, -1);
    }

    private static int getUserIndexInputOfOptionList(TerminalCommandLayout terminalCommandLayout, StringBuilder msg, List<?> list, int minRange) {
        msg.insert(0, "\n");
        for (int i = 0; i < list.size(); i++) {
            msg.append('(').append((i + 1)).append(") ").append(list.get(i)).append("\n");
        }
        System.out.println(msg);
        return SafeScannerInput.getCertainIntForSwitch(terminalCommandLayout, minRange, list.size());
    }

    public static void initProcessWithOnlySituation(String processSituation) {
        LoggerProcessStack.addWithInnerPrefix(processSituation);
        System.out.println(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()));
        LoggerProcessStack.pop();
    }

    public static void initProcess(String processName, String processSituation) {
        LoggerProcessStack.add(processName);
        LoggerProcessStack.addWithInnerPrefix(processSituation);
        System.out.println(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()));
        LoggerProcessStack.pop();
    }

    public static void destroyProcess(ColorfulTextFunction function, int popVal) {
        System.out.println(function.apply(LoggerProcessStack.getAllInOrder()));
        LoggerProcessStack.popLoop(popVal);
    }

    public static void destroyProcessSuccessfully(int popVal) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
        destroyProcess(ColorfulTextDesign::getSuccessColorText, popVal);
    }

    public static void printSuccessfullyCoreProcessLogData() {
//        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
        String result = getCoreLoggerDataWithRequestedPrefix(MetaData.PROCESS_COMPLETED);
        System.out.println(ColorfulTextDesign.getSuccessColorText(result));
    }

    private static String getCoreLoggerDataWithRequestedPrefix(String processResult) {
        return LoggerProcessStack.getCoreProcess(3) + MetaData.INNER_PROCESS_PREFIX + processResult;
    }


    public static void destroyProcessSuccessfully() {
        destroyProcessSuccessfully(2);
    }

    public static void destroyProcessCancelled(int popVal) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
        destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, popVal);
    }

    public static void destroyProcessCancelled() {
        destroyProcessCancelled(2);
    }

    public static void destroyProcessFailed(int popVal) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
        destroyProcess(ColorfulTextDesign::getErrorColorText, popVal);
    }

    public static void destroyProcessFailed() {
        destroyProcessFailed(2);
    }


    public static void destroyProcessExiting(int popVal) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.EXITING_FROM_PROCESS);
        destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, popVal);
    }

    public static void destroyProcessExiting() {
        destroyProcessExiting(2);
    }

    public static void destroyProcessWithoutPrint(int popVal) {
        LoggerProcessStack.popLoop(popVal);
    }

    public static void destroyProcessWithoutPrint() {
        LoggerProcessStack.popLoop(1);
    }

    public static void printSlash() {
        System.out.println("-------------------------");
    }

    public static void printSuccessResult(String msg) {
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + msg);
    }

    public static void printColorfulSuccessResult(String msg) {
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + msg));
    }

    public static void printErrorResult(String msg) {
        System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX) + msg);
    }

    public static void printColorfulErrorResult(String msg) {
        System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + msg));
    }

    public static void printCancelResult(String msg) {
        System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX) + msg);
    }

    public static void printColorfulCancelResult(String msg) {
        System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + msg));
    }

    public static void printWarningResult(String msg) {
        System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX) + msg);
    }

    public static void printColorfulWarningResult(String msg) {
        System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX + msg));
    }

    public static void printInfoResult(String msg) {
        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_RESULT_PREFIX) + msg);
    }

    public static void printColorfulInfoResult(String msg) {
        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_RESULT_PREFIX + msg));
    }

    public static void printArrResult(List<?> list) {
        if (list.isEmpty()) {
            printWarningResult("Not found any data.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + (i + 1) + "-) " + list.get(i));
        }
    }

    public static void getAllInOrderWithAvailableOptions() {
        System.out.print(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
    }


    public static String getSafeStringInputFromTerminalProcess(TerminalCommandLayout interlayout, String title) {
        String input = SafeScannerInput.getStringNotBlank(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return null;
        }
        if (input.equalsIgnoreCase(Integer.toString(EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()))) {
            return getSafeStringInputFromTerminalProcess(interlayout, title);
        }
        return input;
    }

    public static int getSafeIntInputFromTerminalProcess(TerminalCommandLayout interlayout, String title) {
        int input = SafeScannerInput.getCertainIntSafe(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }
        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeIntInputFromTerminalProcess(interlayout, title);
        }
        return input;
    }

    public static int getSafeIntInputFromTerminalProcess(TerminalCommandLayout interlayout, String title, int min, int max) {
        int input = SafeScannerInput.getCertainIntSafe(interlayout, title, min, max);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }
        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeIntInputFromTerminalProcess(interlayout, title, min, max);
        }
        return input;
    }

    public static double getSafeDoubleInputFromTerminalProcess(TerminalCommandLayout interlayout, String title, int min, int max) {
        double input = SafeScannerInput.getCertainDoubleSafe(interlayout, title, min, max);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }
        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeDoubleInputFromTerminalProcess(interlayout, title, min, max);
        }
        return input;
    }

    public static int getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(TerminalCommandLayout interlayout, List<?> list) {
        int input = getIndexValueOfMsgListIncludesExit(interlayout, list);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }

        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, list);
        }
        return input;
    }

    public static int getSafeIndexValueOfMsgListIncludeExistFromTerminalProcessWithOptionMsg(TerminalCommandLayout interlayout, String msg, List<?> list) {
        int input = getIndexValueOfMsgListIncludesExitWitchCustomSelectionOptionMessage(interlayout, msg, list);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }

        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, list);
        }
        return input;
    }


    public static int getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(TerminalCommandLayout interlayout, List<?> list) {
        int input = getIndexValueOfMsgListIncludesCancelAndSaveExits(interlayout, list);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            return Integer.MIN_VALUE;
        }

        if (input == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            return getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(interlayout, list);
        }
        return input;
    }


}
