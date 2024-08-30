package org.aes.compare.orm;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.*;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerConfigORM;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ORMApp extends TerminalCommandLayout {
    private final ORMConfigSingleton orm = new ORMConfigSingleton();
    private AddressFacade addressFacade;
    private StudentFacade studentFacade;
    private CourseFacade courseFacade;
    private ExamResultFacade examResultFacade;
    private SettingFacade settingFacade;//= new SettingFacade(this);

    public ORMApp() {
        runInitConf();
    }

    private void greetUser() {
        System.out.println("Hello. Welcome to program.\nPlease type enter to go on.");
    }

    private void runInitConf() {
//        musicPlayer.start();
        ORMConfigSingleton.enableJPA();
        resetORMServices();
        LoggerConfigORM.disable();
//        ColorfulTextDesign.enableCMDPrinting();
//        settingFacade.updatePrintingSetting();
    }

    public void start() {
//        runInitConf();
        ColorfulTextDesign.enableIDEPrinting();
        greetUser();

        LoggerProcessStack.add(MetaData.PROCESS_PREFIX_MAIN);

        int option = -1;
        while (isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("Exam Result");
            indexes.add("Setting");

            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(this, indexes);
            if (FacadeUtility.isOptionEqualsToRunForCMD(option)) {
                continue;
            }
            switch (option) {

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
                    updateSettingScenario();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }

            if (option > 0) {
                LoggerProcessStack.pop();
            }
        }

    }

    void addressScenario() {
        int option;
        addressFacade.enableNextProcess();
        while (addressFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Id)");
            indexes.add("Update");
            indexes.add("Delete");


            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(addressFacade, indexes);
            if (FacadeUtility.isCancelledProcess(addressFacade)) {
                FacadeUtility.destroyProcessExiting(1);
                return;
            }
            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    return;
                case 1:
                    addressFacade.save();
                    break;
                case 2:
                    addressFacade.findAll();
                    break;
                case 3:
                    addressFacade.findByMultipleWay();
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

    void studentScenario() {
        int option = -1;
        studentFacade.enableNextProcess();
        while (studentFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Id)");
            indexes.add("Find Student's all courses ");
            indexes.add("Find By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");

            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(studentFacade, indexes);

            if (FacadeUtility.isCancelledProcess(studentFacade)) {
                FacadeUtility.destroyProcessExiting(1);
                return;
            }
            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    return;
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

    void courseScenario() {
        int option = -1;
        courseFacade.enableNextProcess();
        while (courseFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Name)");
            indexes.add("Find All Courses Belongs to Student");
            indexes.add("Update");
            indexes.add("Delete");

            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(courseFacade, indexes);
            if (FacadeUtility.isCancelledProcess(courseFacade)) {
                FacadeUtility.destroyProcessExiting(1);
                return;
            }

            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    return;
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

    void examResultScenario() {
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

        examResultFacade.enableNextProcess();
        while (examResultFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find All By Student (Id)");
            indexes.add("Find All By Course (Name)");
            indexes.add("Find All By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");

            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(examResultFacade, indexes);
            if (FacadeUtility.isCancelledProcess(examResultFacade)) {
                FacadeUtility.destroyProcessExiting(1);
                return;
            }
            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    return;
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

    private void updateSettingScenario() {

        settingFacade.enableNextProcess();
        while (settingFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Select ORM Tool (JPA-Hibernate)");
            indexes.add("ORM Logs (Enable-Disable)");
            indexes.add("Music (On-Off)");
            indexes.add("Printing Setting (CMD - IDE)");
            indexes.add("Update Terminal Prefix");

            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(settingFacade, indexes);
            if (FacadeUtility.isCancelledProcess(settingFacade)) {
                FacadeUtility.destroyProcessExiting(1);
                return;
            }
            switch (option) {
                case 0:
                    FacadeUtility.destroyProcessExiting(1);
                    return;
                case 1:
                    settingFacade.updateORMSetting();
                    break;
                case 2:
                    settingFacade.updateORMLogSettings();
                    break;
                case 3:
                    settingFacade.updateMusicSetting();
                    break;
                case 4:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_PRINTING);
                    FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
                    settingFacade.updatePrintingSetting();
                    break;
                case 5:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_TERMINAL_SETTING);
                    FacadeUtility.initProcessWithOnlySituation(MetaData.PROCESS_STARTS);
                    settingFacade.updateTerminalPrefixSetting();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
        }
    }


    /*static*/
    public void resetORMServices() {
        settingFacade = new SettingFacade(this);
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(), addressFacade, courseFacade);
        courseFacade.setStudentFacade(studentFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getCourseService(), courseFacade, orm.getStudentService(), studentFacade);
    }

    public AddressFacade getAddressFacade() {
        return addressFacade;
    }

    public ORMConfigSingleton getOrm() {
        return orm;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public CourseFacade getCourseFacade() {
        return courseFacade;
    }

    public ExamResultFacade getExamResultFacade() {
        return examResultFacade;
    }

    public SettingFacade getSettingFacade() {
        return settingFacade;
    }
}