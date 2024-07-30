package org.aes.compare.orm;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.AddressFacade;
import org.aes.compare.orm.consoleapplication.CourseFacade;
import org.aes.compare.orm.consoleapplication.ExamResultFacade;
import org.aes.compare.orm.consoleapplication.StudentFacade;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.orm.utility.MusicPlayer;
import org.aes.compare.uiconsole.business.LoggerConfigORM;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ORMConfigSingleton orm = new ORMConfigSingleton();
    private static AddressFacade addressFacade;
    private static StudentFacade studentFacade;
    private static CourseFacade courseFacade;
    private static ExamResultFacade examResultFacade;
    private static MusicPlayer musicPlayer = new MusicPlayer();

    private static void runInitConf() {
        musicPlayer.start();
        LoggerConfigORM.disable();
        ColorfulTextDesign.enableCMDPrinting();
        System.out.println("Hello. Welcome to program.\nPlease select where do you run the project.");
        updatePrintingSetting();
        ORMConfigSingleton.enableJPA();
        resetORMServices();
    }
    public static void main(String[] args) {
        runInitConf();
        LoggerProcessStack.add(MetaData.PROCESS_PREFIX_MAIN);

        int globalOption = -1;
        while (true) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("Exam Result");
            indexes.add("Setting");

            globalOption = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_GLOBAL, indexes);
            switch (globalOption) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    System.exit(0);
                    break;
                case 1:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
                    addressScenario();
                    break;
                case 2:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
                    studentScenario();
                    break;
                case 3:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
                    courseScenario();
                    break;
                case 4:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_EXAM_RESULT);
                    examResultScenario();
                    break;
                case 5:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_SETTINGS);
                    updateSetting();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }

            if (globalOption > 0) {
                LoggerProcessStack.pop();
            }
        }
    }

    static void addressScenario() {
        int option = -1;
        while (option != 0) {

            List<String> indexes = new ArrayList<>();
            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Id)");
            indexes.add("Update");
            indexes.add("Delete");


            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, indexes);
            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    break;
                case 1:
                    addressFacade.save();
                    break;
                case 2:
                    addressFacade.findAll();
                    break;
                case 3:
                    addressFacade.findById();
                    break;
                case 4:
                    addressFacade.updateAddressProcess();
                    break;
                case 5:
                    addressFacade.delete();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
        }
    }

    static void studentScenario() {
        int option = -1;
        while (option != 0) {

            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Id)");
            indexes.add("Find Student's all courses ");
            indexes.add("Find By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");

            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);

            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    break;
                case 1:
                    studentFacade.save();
                    break;
                case 2:
                    studentFacade.findAll();
                    break;
                case 3:
                    studentFacade.findByMultipleWay();
                    break;
                case 4:
                    studentFacade.findStudentAllCourses();
                    break;
                case 5:
                    studentFacade.findByStudentIdWithCourseName();
                    break;
                case 6:
                    studentFacade.update();
                    break;
                case 7:
                    studentFacade.delete();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
        }
    }

    static void courseScenario() {
        int option = -1;
        while (option != 0) {

            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Name)");
            indexes.add("Find All Courses Belongs to Student");
            indexes.add("Update");
            indexes.add("Delete");

            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, indexes);

            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    break;
                case 1:
                    courseFacade.save();
                    break;
                case 2:
                    courseFacade.findAll();
                    break;
                case 3:
                    courseFacade.findByName();
                    break;
                case 4:
                    courseFacade.findAllCoursesBelongsToStudent();
                    break;
                case 5:
                    courseFacade.update();
                    break;
                case 6:
                    courseFacade.delete();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
        }
    }

    static void examResultScenario() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        boolean resultStudent = studentFacade.isAnyStudentSaved();
        if (resultStudent) {
            FacadeUtility.destroyProcessSuccessfully(2);
            FacadeUtility.printColorfulSuccessResult("Found saved students.");
        }

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        boolean resultCourse = courseFacade.isAnyCourseSaved();
        if (resultCourse) {
            FacadeUtility.destroyProcessSuccessfully(2);
            FacadeUtility.printColorfulSuccessResult("Found saved courses.");
        }

        if (!resultStudent || !resultCourse) {
            ColorfulTextDesign.getErrorColorText("Without creating any Student or Course, you may not run the functions of the Exam Result processes.\nStrongly recommend to create " +
                    ColorfulTextDesign.getInfoColorText("Student") +
                    ColorfulTextDesign.getErrorColorText(" and ") +
                    ColorfulTextDesign.getInfoColorText("Course") +
                    ColorfulTextDesign.getErrorColorText(" data before visit this option. "));
        }
        int option = -1;
        while (option != 0) {

            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find All By Student (Id)");
            indexes.add("Find All By Course (Name)");
            indexes.add("Find All By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");

            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);

            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    break;
                case 1:
                    examResultFacade.save();
                    break;
                case 2:
                    examResultFacade.findAll();
                    break;
                case 3:
                    examResultFacade.findAllByStudentId();
                    break;
                case 4:
                    examResultFacade.findAllByCourseName();
                    break;
                case 5:
                    examResultFacade.findAllByStudentIdAndCourseName();
                    break;
                case 6:
                    examResultFacade.update();
                    break;
                case 7:
                    examResultFacade.delete();
                    break;

                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
        }
    }

    private static void updateSetting() {
        List<String> indexes = new ArrayList<>();
        indexes.add("Select ORM Tool (JPA-Hibernate)");
        indexes.add("ORM Logs (Enable-Disable)");
        indexes.add("Music (On-Off)");
        indexes.add("Printing Setting (CMD - IDE)");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 0:
                FacadeUtility.destroyProcessExiting(1);
                break;
            case 1:
                updateORMSetting();
                updateSetting();
                break;
            case 2:
                updateORMLogSettings();
                updateSetting();
                break;
            case 3:
                updateMusicSetting();
                updateSetting();
                break;
            case 4:
                LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_PRINTING);
                FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
                updatePrintingSetting();
                updateSetting();
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }


    }
    private static void updateORMSetting() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_SELECT);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("JPA");
        indexes.add("Hibernate");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 0:
                FacadeUtility.destroyProcessCancelled();
                break;
            case 1:
                ORMConfigSingleton.enableJPA();
                resetORMServices();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "JPA is activated "));
                break;
            case 2:
                ORMConfigSingleton.enableHibernate();
                resetORMServices();
                FacadeUtility.destroyProcessSuccessfully();
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX + "Hibernate is activated "));
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }
    }

    private static void updateORMLogSettings() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_ORM_LOGS);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable ORM Logs");
        indexes.add("Disable ORM Logs");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
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

    private static void updateMusicSetting() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_MUSIC);
        FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable Music");
        indexes.add("Disable Music");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
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
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX+ "Music is paused."));
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    private static void updatePrintingSetting() {
        List<String> indexes = new ArrayList<>();
        indexes.add("CMD (Windows Command Line)");
        indexes.add("IDE (Java Ide)");
        indexes.add("Standard (Default)");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
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

    static void resetORMServices() {
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(), addressFacade, courseFacade);
        courseFacade.setStudentFacade(studentFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getCourseService(), courseFacade, orm.getStudentService(), studentFacade);
    }
}