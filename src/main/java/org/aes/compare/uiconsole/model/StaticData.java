package org.aes.compare.uiconsole.model;

import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    private static final List<String> globalProcessOptions = new ArrayList<>();

    private static void fillGlobalProcessOptions() {
        globalProcessOptions.add("CRUD > Object  (First select CRUD process, then select Object)");
        globalProcessOptions.add("Object > CRUD  (First select Object, then select CRUD process)");
        globalProcessOptions.add("Learn commands to use more effectively");
        globalProcessOptions.add(printSameListAgain());
    }

    public static List<String> getGlobalProcessOptions() {
        if (globalProcessOptions.size() == 0) {
            fillGlobalProcessOptions();
        }
        return globalProcessOptions;
    }

    private static String printSameListAgain() {
        return "Print list again";
    }


    public static List<String> getCustomTerminalHelpInfoData() {
        List<String> helpInfos = new ArrayList<>();

        helpInfos.add(getBlueText("---------------------------"));
        helpInfos.add(ColorfulTextDesign.getWarningColorText("[*] Standard Commands: "));
        helpInfos.add(getDesignedText("-q"," : " , "Quit current process"));
        helpInfos.add(getDesignedText("-e"," : " , "Exit program"));
        helpInfos.add(getDesignedText("-h"," : " , "Help"));
//        helpInfos.add("-l : list");
        helpInfos.add(getBlueText("-----------"));

        helpInfos.add(ColorfulTextDesign.getWarningColorText("[*] CRUD Functions: "));
        helpInfos.add(getDesignedText("-c"," : " ,"Create"));
        helpInfos.add(getDesignedText("-r"," : " ,"Read"));
        helpInfos.add(getDesignedText("-u"," : " ,"Update"));
        helpInfos.add(getDesignedText("-d"," : " ,"Delete"));

        helpInfos.add(getBlueText("-----------"));
        helpInfos.add(ColorfulTextDesign.getWarningColorText("[*] Objects : "));
        helpInfos.add(getDesignedText("-A", " : " , "Address (Object)"));
        helpInfos.add(getDesignedText("-S", " : " , "Student (Object)"));
        helpInfos.add(getDesignedText("-C", " : " , "Course (Object)"));
        helpInfos.add(getDesignedText("-ER", " : " , "ExamResult (Object)"));

        helpInfos.add(getBlueText("-----------"));

        helpInfos.add(ColorfulTextDesign.getWarningColorText("[*] Example Use : "));
        helpInfos.add(getDesignedText("-c -C", " --> ", "Create Course"));
        helpInfos.add(getDesignedText("-r -S", " --> ", "Read Student data"));
        helpInfos.add(getDesignedText("-q", " --> ", "Cancel current process"));
        helpInfos.add(getDesignedText("-e", " --> ", "Exit program"));
        helpInfos.add(getGreenText("---------------------------"));
        return helpInfos;
    }

    private static String getDesignedText(String key, String middleItem, String desc) {
        return getBlueText(key) + getPurpleText(middleItem) + desc;
    }

    private static String getBlueText(String text) {
        return ColorfulTextDesign.getInfoColorText(text);
    }

    private static String getGreenText(String text) {
        return ColorfulTextDesign.getSuccessColorText(text);
    }

    private static String getPurpleText(String text) {
        return ColorfulTextDesign.getWarningColorText(text);
    }
}
