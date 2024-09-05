package org.ahmeteminsaglik.uiconsole.business;

public class LoggerConfigORM {
    private static boolean printORMLogs = true;

    public static void enable() {
        printORMLogs = true;
    }

    public static void disable() {
        printORMLogs = false;
    }

    public static boolean isAllowedPrint() {
        return printORMLogs;
    }

}
