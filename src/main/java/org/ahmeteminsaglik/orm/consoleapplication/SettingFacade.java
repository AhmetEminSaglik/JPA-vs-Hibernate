package org.ahmeteminsaglik.orm.consoleapplication;

import org.ahmeteminsaglik.customterminal.business.abstracts.TerminalCommandLayout;
import org.ahmeteminsaglik.customterminal.business.concretes.InnerTerminalProcessLayout;
import org.ahmeteminsaglik.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.ORMApp;
import org.ahmeteminsaglik.orm.config.ORMConfigSingleton;
import org.ahmeteminsaglik.orm.consoleapplication.utility.FacadeUtility;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;
import org.ahmeteminsaglik.orm.utility.MusicPlayer;
import org.ahmeteminsaglik.uiconsole.business.LoggerConfigORM;
import org.ahmeteminsaglik.uiconsole.business.LoggerProcessStack;
import org.ahmeteminsaglik.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SettingFacade extends TerminalCommandLayout {
    private final ORMApp ormApp;
    private final static MusicPlayer musicPlayer = new MusicPlayer();

    public SettingFacade(ORMApp ormApp) {
        this.ormApp = ormApp;
    }

    public void updateORMSetting() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_SELECT);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("JPA");
        indexes.add("Hibernate");

        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessExiting(2);
            return;
        }
        switch (option) {
            case 0:
                FacadeUtility.destroyProcessCancelled();
                break;
            case 1:
                ORMConfigSingleton.enableJPA();
                ormApp.resetORMServices();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "JPA is activated "));
                break;
            case 2:
                ORMConfigSingleton.enableHibernate();
                ormApp.resetORMServices();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "Hibernate is activated "));
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }
    }

    public void updateORMLogSettings() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_LOGS);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable ORM Logs");
        indexes.add("Disable ORM Logs");

        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessExiting(2);
            return;
        }
        switch (option) {


            case 0:
                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                FacadeUtility.destroyProcessCancelled();
                break;
            case 1:
                LoggerConfigORM.enable();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "ORM Logs are " + ColorfulTextDesign.getSuccessColorText("enabled") + ".");
                break;
            case 2:
                LoggerConfigORM.disable();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "ORM Logs are " + ColorfulTextDesign.getSuccessColorText("disabled") + ".");
                break;

            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    public void updateMusicSetting() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_MUSIC);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable Music");
        indexes.add("Disable Music");

        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessExiting(2);
            return;
        }
        switch (option) {
            case 0:
                FacadeUtility.destroyProcessCancelled();
                break;
            case 1:
                musicPlayer.resume();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "Music is activated."));
                break;
            case 2:
                musicPlayer.pause();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "Music is paused."));
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    public void updatePrintingSetting() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        List<String> indexes = new ArrayList<>();
        indexes.add("CMD (Windows Command Line)");
        indexes.add("IDE (Java Ide)");
        indexes.add("Standard (Default)");
        String msg = MetaData.PRINTING_SETTING_SELECT_OPTION_WHERE_DO_YOU_RUN_YOUR_PROGRAM;
        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcessWithOptionMsg(interlayout, msg, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return;
        }
        switch (option) {
            case 0:
                FacadeUtility.destroyProcessCancelled();
                break;
            case 1:
                ColorfulTextDesign.enableCMDPrinting();
                FacadeUtility.destroyProcessSuccessfully();
                break;
            case 2:
                ColorfulTextDesign.enableIDEPrinting();
                FacadeUtility.destroyProcessSuccessfully();
                break;
            case 3:
                ColorfulTextDesign.enableStandardPrinting();
                FacadeUtility.destroyProcessSuccessfully();
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    public void updateTerminalPrefixSetting() {

        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        String newPrefix = getNewPrefixInput();

//        if (FacadeUtility.isCancelledProcess(interlayout)) {
//            FacadeUtility.destroyProcessCancelled();
//            return;
//        }
        if (confirmAction(newPrefix)) {
            CMDLineSingletonBuilder.getCmdLine().updateOneLineCommandPrefix(newPrefix);
            FacadeUtility.destroyProcessSuccessfully();
        } else {
            FacadeUtility.destroyProcessCancelled();
        }
    }

    private String getNewPrefixInput() {
        System.out.println("Current terminal command prefix : [" + CMDLineSingletonBuilder.getCmdLine().getPrefix() + "]");
        System.out.println("Type new terminal command prefix : ");
        Scanner scanner = new Scanner(System.in);
        String newPrefix = scanner.nextLine();
        if (newPrefix.isBlank()) {
            FacadeUtility.printColorfulErrorResult("Empty String is not allowed for new prefix. Please try again.");
            return getNewPrefixInput();
        }
        return newPrefix;
    }

    private boolean confirmAction(String newPrefix) {
        System.out.println("Do you confirm new terminal command prefix for [" + newPrefix + "]." +
                "\n(1) - Yes" +
                "\n(0) - No");
        int val = SafeScannerInput.getCertainIntSafe(0, 1);
        if (val == 1) {
            return true;
        }
        return false;
    }

    public void startMusic() {
        musicPlayer.start();
    }
}
