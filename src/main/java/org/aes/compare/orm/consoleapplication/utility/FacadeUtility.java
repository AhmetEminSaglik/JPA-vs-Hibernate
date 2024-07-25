package org.aes.compare.orm.consoleapplication.utility;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerProcessStack;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;

public class FacadeUtility {
    public static StringBuilder createMsgFromListExit(List<?> list) {
        StringBuilder sb = new StringBuilder();
//        sb.append('(').append(0).append(") Exit\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append('(').append((i + 1)).append(") ").append(list.get(i)).append("\n");
        }
        return sb;
    }

    public static StringBuilder createMsgFromListWithSaveAndCancelExit(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(-1).append(") Cancel & Exit\n");
        sb.append('(').append(0).append(") Save & Exit\n");

        for (int i = 0; i < list.size(); i++) {
            sb.append('(').append((i + 1)).append(") ").append(list.get(i)).append("\n");
        }
        return sb;
    }

    public static int getIndexValueOfMsgListIncludesExit(String objectPrefix, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
        msg.append('(').append(0).append(") Exit\n");
        return getUserIndexInputOfOptionList(msg, list, 0);

    }

    public static int getIndexValueOfMsgListIncludesCancelAndExit(String objectPrefix, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
        msg.append('(').append(0).append(") Cancel & Exit\n");
        return getUserIndexInputOfOptionList(msg, list, 0);
    }

    public static int getIndexValueOfMsgListIncludesCancelAndSaveExits(String objectPrefix, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()) + MetaData.AVAILABLE_OPTIONS);
        msg.append('(').append(-1).append(") Cancel & Exit\n");
        msg.append('(').append(0).append(") Save & Exit\n");
        return getUserIndexInputOfOptionList(msg, list, -1);
    }

    private static int getUserIndexInputOfOptionList(StringBuilder msg, List<?> list, int minRange) {
//        msg.append(createMsgFromListExit(list));
        msg.insert(0,"\n");
        for (int i = 0; i < list.size(); i++) {
            msg.append('(').append((i + 1)).append(") ").append(list.get(i)).append("\n");
        }
        System.out.println(msg);
        return SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, minRange, list.size());
    }


    /*public static int getIndexValueOfMsgListIncludesCancelAndSaveExits(String objectPrefix, List<?> list) {

        StringBuilder msg = new StringBuilder();
        msg.append('(').append(-1).append(") Cancel & Exit\n");
        msg.append('(').append(0).append(") Save & Exit\n");

        msg.append(createMsgFromListExit(list));
        for (int i = 0; i < list.size(); i++) {
            msg.append('(').append((i + 1)).append(") ").append(list.get(i)).append("\n");
        }
//        return sb;

        msg.insert(0, objectPrefix + MetaData.AVAILABLE_OPTIONS);
//        System.out.println(msg);
//        int option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, list.size());

        System.out.println(msg);
        return SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, -1, list.size());


    }*/

    /*public static void printIndexesData(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(0).append(") Cancel And Exit");

        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1)).append("-) ").append(list.get(i)).append("\n");
        }
        System.out.println(sb);
    }*/

    public static void initProcess(String processName, String processSituation) {
        LoggerProcessStack.add(processName);
        LoggerProcessStack.addWithInnerPrefix(processSituation);
        //        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_ADDRESS + MetaData.PROCESS_SAVE + MetaData.PROCESS_STARTS));
        System.out.println(ColorfulTextDesign.getInfoColorText(LoggerProcessStack.getAllInOrder()));
        LoggerProcessStack.pop();
    }

//    public static void destroyProcess(String processSituation, ColorfulTextFunction function, int popVal) {
//        LoggerProcessStack.add(processSituation);
//        destroyProcess(function, popVal);
//    }

    public static void destroyProcess(ColorfulTextFunction function, int popVal) {
        System.out.println(function.apply(LoggerProcessStack.getAllInOrder()));
        LoggerProcessStack.popLoop(popVal);
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

}
