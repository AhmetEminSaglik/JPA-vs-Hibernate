package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.ORMApp;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.orm.utility.MusicPlayer;
import org.aes.compare.uiconsole.business.LoggerConfigORM;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;

public class SettingFacade extends TerminalCommandLayout {
    private ORMApp ormApp;

    public SettingFacade(ORMApp ormApp) {
        this.ormApp = ormApp;
    }

    private /*static*/ MusicPlayer musicPlayer = new MusicPlayer();

    public  /*static*/ void updateORMSetting() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_SELECT);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("JPA");
        indexes.add("Hibernate");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(this, MetaData.PROCESS_PREFIX_SETTINGS, indexes);
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
            case 200:
                System.out.println("200 OK dondu");
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }
    }

    public  /*static*/ void updateORMLogSettings() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_LOGS);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable ORM Logs");
        indexes.add("Disable ORM Logs");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(this, MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 200:
                System.out.println("200 OK dondu");
                break;
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

    public  /*static*/ void updateMusicSetting() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_MUSIC);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable Music");
        indexes.add("Disable Music");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(this, MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 200:
                System.out.println("200 OK dondu");
                break;
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

    public  /*static*/ void updatePrintingSetting() {
        List<String> indexes = new ArrayList<>();
        indexes.add("CMD (Windows Command Line)");
        indexes.add("IDE (Java Ide)");
        indexes.add("Standard (Default)");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(this, MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 200:
                System.out.println("200 OK dondu");
                break;
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

}
