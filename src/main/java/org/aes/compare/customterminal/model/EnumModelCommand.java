package org.aes.compare.customterminal.model;

public enum EnumModelCommand {
    STUDENT("-S", "Student"),
    ADDRESS("-A", "Address"),
    COURSE("-C", "Course"),
    EXAM_RESULT("-ER", "ExamResult");
    String name;
    String shortName;

    EnumModelCommand(String shortcutName, String name) {
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
