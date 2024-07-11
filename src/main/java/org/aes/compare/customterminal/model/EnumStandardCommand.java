package org.aes.compare.customterminal.model;

public enum EnumStandardCommand {
    QUIT_PROCESS("-q", "quit"),
    QUIT_PROGRAM("-e", "exit"),
    HELP("-h", "help"),
    YES("-y", "yes"),
    NO("-n", "no");
    String shortName;
    String longName;

    EnumStandardCommand(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
