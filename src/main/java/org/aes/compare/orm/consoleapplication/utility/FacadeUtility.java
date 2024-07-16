package org.aes.compare.orm.consoleapplication.utility;

import java.util.List;

public class FacadeUtility {
    public static StringBuilder createMsgFromListExit(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(0).append(") Exit\n");

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

    /*public static void printIndexesData(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(0).append(") Cancel And Exit");

        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1)).append("-) ").append(list.get(i)).append("\n");
        }
        System.out.println(sb);
    }*/
}
