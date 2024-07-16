package org.aes.compare.orm.consoleapplication.utility;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.utility.ColorfulTextDesign;
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
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(objectPrefix) + MetaData.PROCESS_LIST);
        msg.append('(').append(0).append(") Exit\n");
        return getUserIndexInputOfOptionList(msg, list,0);

    }

    public static int getIndexValueOfMsgListIncludesCancelAndExit(String objectPrefix, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(objectPrefix) + MetaData.PROCESS_LIST);
        msg.append('(').append(0).append(") Cancel & Exit\n");
        return getUserIndexInputOfOptionList(msg, list,0);
    }

    public static int getIndexValueOfMsgListIncludesCancelAndSaveExits(String objectPrefix, List<?> list) {
        StringBuilder msg = new StringBuilder(ColorfulTextDesign.getInfoColorText(objectPrefix) + MetaData.PROCESS_LIST);
        msg.append('(').append(-1).append(") Cancel & Exit\n");
        msg.append('(').append(0).append(") Save & Exit\n");
        return getUserIndexInputOfOptionList(msg, list,-1);
    }

    private static int getUserIndexInputOfOptionList(StringBuilder msg, List<?> list,int minRange) {
//        msg.append(createMsgFromListExit(list));
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

        msg.insert(0, objectPrefix + MetaData.PROCESS_LIST);
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
}
