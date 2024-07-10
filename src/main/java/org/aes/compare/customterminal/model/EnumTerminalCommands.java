package org.aes.compare.customterminal.model;

public enum EnumTerminalCommands {
    QUIT("-q", "quit"),
    YES("-y", "yes"),
    NO("-n", "no");
    String shortName;
    String longName;

    EnumTerminalCommands(String shortName, String longName) {
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
