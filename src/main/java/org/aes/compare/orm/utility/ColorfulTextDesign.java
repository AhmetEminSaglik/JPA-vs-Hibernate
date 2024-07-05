package org.aes.compare.orm.utility;

import org.ahmeteminsaglik.config.PrintConsoleServiceConfig;
import org.ahmeteminsaglik.config.PrintableConsoleServiceManager;
import org.ahmeteminsaglik.printable.abstracts.PrintableConsoleService;
import org.ahmeteminsaglik.utility.ConsoleColors;

public class ColorfulTextDesign {
    private static PrintableConsoleService ps = new PrintableConsoleServiceManager();

    public static String getText(String msg) {
        PrintConsoleServiceConfig.setIdeColor(ConsoleColors.BLUE_BRIGHT);
        return ps.getColorfulText(msg);
    }

    public static String getText(String color, String msg) {
        return ps.getColorfulText(color + msg + ConsoleColors.RESET);
    }

    public static String getErrorColorText(String msg) {
        return ps.getColorfulText(ConsoleColors.PURPLE_BOLD_BRIGHT + msg + ConsoleColors.RESET);
    }

}
