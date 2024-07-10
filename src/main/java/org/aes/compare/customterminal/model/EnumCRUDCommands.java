package org.aes.compare.customterminal.model;

public enum EnumCRUDCommands {
    CREATE("-c", "create"),
    READ("-r", "read"),
    UPDATE("-u", "update"),
    DELETE("-d", "delete");
    String shortName;
    String longName;

    EnumCRUDCommands(String shortName, String longName) {
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
