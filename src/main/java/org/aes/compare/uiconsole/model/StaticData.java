package org.aes.compare.uiconsole.model;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    private static List<String> globalProcessOptions = new ArrayList<>();

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

        helpInfos.add("---------------------------");
        helpInfos.add("[*] Standard Commands: ");
        helpInfos.add("-q : Quit current process");
        helpInfos.add("-e : Exit program");
        helpInfos.add("-h : Help");
        helpInfos.add("-l : list");
        helpInfos.add("---------------------------");

        helpInfos.add("[*] CRUD Functions: ");
        helpInfos.add("-c : Create");
        helpInfos.add("-r : Read");
        helpInfos.add("-u : Update");
        helpInfos.add("-d : Delete");

        helpInfos.add("---------------------------");
        helpInfos.add("[*] Objects : ");
        helpInfos.add("-A : Address (Object)");
        helpInfos.add("-S : Student (Object)");
        helpInfos.add("-C : Course (Object)");
        helpInfos.add("-ER : ExamResult (Object)");
        helpInfos.add("---------------------------");
        helpInfos.add("[*] Example Use : ");
        helpInfos.add("-c -C --> Create Course");
        helpInfos.add("-r -S --> Read Student data");
        helpInfos.add("-q --> Cancel current process");
        helpInfos.add("-e --> Exit program");
        helpInfos.add("---------------------------");

        return helpInfos;
    }
}
