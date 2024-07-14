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

    public static String getInfoColorTextWithPrefix(String msg) {
        return ps.getColorfulText(ConsoleColors.BLUE_BRIGHT + "[INFO]: " + msg + ConsoleColors.RESET);
    }

    public static String getInfoColorText(String msg) {
        return ps.getColorfulText(ConsoleColors.BLUE_BRIGHT + msg + ConsoleColors.RESET);
    }
 
    public static String getErrorColorText(String msg) {
        return ps.getColorfulText(ConsoleColors.PURPLE_BOLD_BRIGHT + "[ERROR]: " + msg + ConsoleColors.RESET);
    }
    public static String getTextForCanceledProcess(String msg) {
        return ps.getColorfulText(ConsoleColors.YELLOW_BRIGHT + msg + ConsoleColors.RESET);
    }

    public static String getTextForUserFeedback(String msg) {
        return ps.getColorfulText(ConsoleColors.CYAN_BOLD + msg + ConsoleColors.RESET);
    }

    public static String getSuccessColorTextWithPrefix(String msg) {
        return ps.getColorfulText(ConsoleColors.GREEN_BRIGHT + "[SUCCESS]: " + msg + ConsoleColors.RESET);
    }

    public static String getSuccessColorText(String msg) {
        return ps.getColorfulText(ConsoleColors.GREEN_BRIGHT + msg + ConsoleColors.RESET);
    }



}
