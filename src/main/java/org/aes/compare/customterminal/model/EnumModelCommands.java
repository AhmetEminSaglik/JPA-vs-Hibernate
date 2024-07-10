package org.aes.compare.customterminal.model;

public enum EnumModelCommands {
    STUDENT("-S", "Student"),
    ADDRESS("-A", "Address"),
    COURSE("-C", "Course"),
    EXAM_RESULT("-ER", "ExamResult");
    String name;
    String shortName;

    EnumModelCommands(String shortcutName, String name) {
        this.name = name;
        this.shortName = shortcutName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
