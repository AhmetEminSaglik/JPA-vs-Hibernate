package org.aes.compare.orm;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.*;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerConfigORM;
import org.aes.compare.uiconsole.business.LoggerProcessStack;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;
import org.aes.compare.uiconsole.utility.InputParserTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ORMApp extends TerminalCommandLayout {
    private /*static*/ final ORMConfigSingleton orm = new ORMConfigSingleton();
    private /*static*/ AddressFacade addressFacade;
    private /*static*/ StudentFacade studentFacade;
    private /*static*/ CourseFacade courseFacade;
    private /*static*/ ExamResultFacade examResultFacade;
    private SettingFacade settingFacade;//= new SettingFacade(this);

    private final Scanner scanner = new Scanner(System.in);
    private final InputParserTree inputParserTree = new InputParserTree();

    private /*static*/ void runInitConf() {
//        musicPlayer.start();
        ORMConfigSingleton.enableJPA();
        resetORMServices();
        LoggerConfigORM.disable();
//        ColorfulTextDesign.enableCMDPrinting();
        ColorfulTextDesign.enableIDEPrinting();
        System.out.println("Hello. Welcome to program.\nPlease select where do you run the project.");
//        settingFacade.updatePrintingSetting();
    }

    public void selectGlobalProcess() {
        String input = scanner.nextLine();
        EnumCMDLineParserResult result = inputParserTree.decideProcess(input);
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_CMDLINE.getId()) {
            TerminalCMD terminalCMD = inputParserTree.getTerminalCMD();
            new TerminalCommandManager().runCustomCommand(this, terminalCMD);
        }
        if (result.getId() == EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE.getId()) {
//            runProcessIndexValue(input);
        }
    }

    public /*static*/ void start() {
        runInitConf();
        LoggerProcessStack.add(MetaData.PROCESS_PREFIX_MAIN);

        int option = -1;
        while (isAllowedCurrentProcess()) {
            System.out.println(getClass().getSimpleName()+" -> isAllowedCurrentProcess() : "+isAllowedCurrentProcess());
            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("Exam Result");
            indexes.add("Setting");

            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(this, MetaData.PROCESS_PREFIX_GLOBAL, indexes);
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

    /*static*/ void addressScenario() {
        int option = -1;
        addressFacade.enableNextProcess();
        while (addressFacade.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By (Id)");
            indexes.add("Update");
            indexes.add("Delete");


            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(addressFacade, MetaData.PROCESS_PREFIX_ADDRESS, indexes);
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

    /*static*/ void studentScenario() {
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

            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(studentFacade, MetaData.PROCESS_PREFIX_STUDENT, indexes);

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

    /*static*/ void courseScenario() {
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

            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(courseFacade, MetaData.PROCESS_PREFIX_COURSE, indexes);
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

    /*static*/ void examResultScenario() {
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

            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(examResultFacade, MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);
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

    private /*static*/ void updateSettingScenario() {

        settingFacade.enableNextProcess();
        while (settingFacade.isAllowedCurrentProcess()) {
        List<String> indexes = new ArrayList<>();
        indexes.add("Select ORM Tool (JPA-Hibernate)");
        indexes.add("ORM Logs (Enable-Disable)");
        indexes.add("Music (On-Off)");
        indexes.add("Printing Setting (CMD - IDE)");

            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(settingFacade, MetaData.PROCESS_PREFIX_SETTINGS, indexes);
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
}