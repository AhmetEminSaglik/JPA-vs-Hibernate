package org.aes.compare.orm;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.AddressFacade;
import org.aes.compare.orm.consoleapplication.CourseFacade;
import org.aes.compare.orm.consoleapplication.ExamResultFacade;
import org.aes.compare.orm.consoleapplication.StudentFacade;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerConfigORM;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ORMConfigSingleton orm = new ORMConfigSingleton();
    private static AddressFacade addressFacade;// = new addressFacadeFacade(orm.getAddressService());
    private static StudentFacade studentFacade;// = new studentFacadeFacade(orm.getStudentService(), orm.getAddressService());
    private static CourseFacade courseFacade;
    private static ExamResultFacade examResultFacade;
    public static void main(String[] args) {
        LoggerConfigORM.disable();
        /*ColorfulTextDesign.enableCMDPrinting();
        System.out.println("Hello. Welcome to program.\nPlease select where do you run the project.");
        updatePrintingSetting();*/
        ColorfulTextDesign.enableIDEPrinting();
        ORMConfigSingleton.enableJPA();
        resetORMServices();

        int globalOption = -1;
        while (globalOption != 0) {

            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("Exam Result");
//            indexes.add("ORM Setting (Switch between JPA - Hibernate)");
            indexes.add("Setting");
            indexes.add("Printing Setting (CMD - IDE)");

            /*StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_GLOBAL + MetaData.AVAILABLE_OPTIONS);
            System.out.println(msg);

            globalOption = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());
            System.out.println();*/
            globalOption = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_GLOBAL, indexes);
            switch (globalOption) {
                case 0:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess("[MAIN]: "+MetaData.EXITING_FROM_PROCESS));
                    break;
                case 1:
                    LoggerProcessStack.add(MetaData.PROCESS_PREFIX_ADDRESS);
                    addressScenario();
                    LoggerProcessStack.pop();
                    break;
                case 2:
                    LoggerProcessStack.add(MetaData.PROCESS_PREFIX_STUDENT);
                    studentScenario();
                    LoggerProcessStack.pop();
                    break;
                case 3:
                    courseScenario();
                    LoggerProcessStack.pop();
                    break;
                case 4:
                    LoggerProcessStack.add(MetaData.PROCESS_PREFIX_EXAM_RESULT);
                    examResultScenario();
                    LoggerProcessStack.pop();
                    break;
                case 5:
                    LoggerProcessStack.add(MetaData.PROCESS_PREFIX_SETTINGS);
                    updateSetting();
                    LoggerProcessStack.pop();
                    break;
                case 6:
                    LoggerProcessStack.add(MetaData.PROCESS_PREFIX_SETTINGS);
                    updatePrintingSetting();
                    LoggerProcessStack.pop();
                    break;
                default:
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
            }
//            System.out.println();
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

           /* StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_ADDRESS + MetaData.AVAILABLE_OPTIONS);
            System.out.println(msg);

                        option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());*/
            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_ADDRESS, indexes);
            switch (option) {
                case 0:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_ADDRESS + " " + MetaData.EXITING_FROM_PROCESS));
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

            /*StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_STUDENT + MetaData.AVAILABLE_OPTIONS);
            System.out.println(msg);

            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());*/
            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);

            switch (option) {
                case 0:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_STUDENT + " " + MetaData.EXITING_FROM_PROCESS));
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

            /*
            StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_COURSE + MetaData.AVAILABLE_OPTIONS);
            System.out.println(msg);
            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());
            */

            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, indexes);

            switch (option) {
                case 0:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_COURSE + " " + MetaData.EXITING_FROM_PROCESS));
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
        boolean result = studentFacade.isAnyStudentSaved();
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)) {
            result = false;
        }
        if (!result) {
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

            /*StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
            msg.insert(0, MetaData.PROCESS_PREFIX_EXAM_RESULT + MetaData.AVAILABLE_OPTIONS);
            System.out.println(msg);

            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());*/
            option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);

            switch (option) {
                case 0:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_EXAM_RESULT + " " + MetaData.EXITING_FROM_PROCESS));
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
        indexes.add("Enable-Disable ORM Logs");


        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_SETTINGS+" "+MetaData.EXITING_FROM_PROCESS));
                break;
            case 1:
                updateORMSetting();
                updateSetting();
//                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            case 2:
                updateORMLogSettings();
                updateSetting();
//                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }


    }
    private static void updateORMSetting() {
        List<String> indexes = new ArrayList<>();
        indexes.add("JPA");
        indexes.add("Hibernate");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                break;
            case 1:
                ORMConfigSingleton.enableJPA();
                resetORMServices();
//                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            case 2:
                ORMConfigSingleton.enableHibernate();
                resetORMServices();
//                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    private static void updateORMLogSettings() {
        List<String> indexes = new ArrayList<>();
        indexes.add("Enable ORM Logs");
        indexes.add("Disable ORM Logs");

        int option = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_SETTINGS, indexes);
        switch (option) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                break;
            case 1:
                LoggerConfigORM.enable();
                System.out.println(ColorfulTextDesign.getSuccessColorText("ORM Logs are enabled."));
                break;
            case 2:
                LoggerConfigORM.disable();
                System.out.println(ColorfulTextDesign.getSuccessColorText("ORM Logs are disabled."));
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
                System.out.println(ColorfulTextDesign.getInfoColorText("Activated Printing Tool : " + ColorfulTextDesign.getCurrentSelectedPrintObjectName()));
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_SETTINGS+" "+MetaData.EXITING_FROM_PROCESS));
                break;
            case 1:
                ColorfulTextDesign.enableCMDPrinting();
                break;
            case 2:
                ColorfulTextDesign.enableIDEPrinting();
                break;
            case 3:
                ColorfulTextDesign.enableStandardPrinting();
                break;
            default:
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.SWITCH_DEFAULT_INVALID_CHOICE));
        }

    }

    /*static private void colorTest() {
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("Selected CMD printing Option"));
        System.out.println(ColorfulTextDesign.getSuccessColorText("Success"));
        System.out.println(ColorfulTextDesign.getSuccessColorTextWithPrefix("Success"));
        System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Error"));
        System.out.println(ColorfulTextDesign.getErrorColorText("Error"));
        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Cancel"));
        System.out.println(ColorfulTextDesign.getInfoColorText("Info"));
        System.out.println(ColorfulTextDesign.getWarningColorText("Warning"));
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("Warning"));
    }*/

    static void resetORMServices() {
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(), addressFacade, courseFacade);
        courseFacade.setStudentFacade(studentFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getCourseService(), courseFacade, orm.getStudentService(), studentFacade);
    }
}