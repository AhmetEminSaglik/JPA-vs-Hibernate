package org.aes.compare.orm;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.AddressFacade;
import org.aes.compare.orm.consoleapplication.CourseFacade;
import org.aes.compare.orm.consoleapplication.ExamResultFacade;
import org.aes.compare.orm.consoleapplication.StudentFacade;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static ORMConfigSingleton orm = new ORMConfigSingleton();
    private static AddressFacade addressFacade;// = new addressFacadeFacade(orm.getAddressService());
    private static StudentFacade studentFacade;// = new studentFacadeFacade(orm.getStudentService(), orm.getAddressService());
    private static CourseFacade courseFacade;
    private static ExamResultFacade examResultFacade;

    public static void main(String[] args) {
        ORMConfigSingleton.enableJPA();
        resetORMServices();
     /*   ORMConfigSingleton.enableHibernate();
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(),addressFacade,courseFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getStudentService(), orm.getCourseService(),studentFacade);*/

        int globalOption = -1;
        while (globalOption != 0) {

            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("Exam Result");
            indexes.add("ORM Setting (Switch between JPA - Hibernate)");
//            indexes.add("Exam Result");


            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            System.out.print(MetaData.SELECT_ONE_OPTION);
            globalOption = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());

            switch (globalOption) {
                case 0:
                    System.out.println(MetaData.EXITING_FROM_PROCESS);
                    break;
                case 1:
                    addressScenario();
                    break;
                case 2:
                    studentScenario();
                    break;
                case 3:
                    courseScenario();
                    break;
                case 4:
                    examResultScenario();
                    break;
                case 5:
                    updateORMSetting();
                    break;
                default:
                    System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
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

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            System.out.print(MetaData.SELECT_ONE_OPTION);
            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());

            switch (option) {
                case 0:
                    System.out.println(MetaData.EXITING_FROM_PROCESS);
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
                    addressFacade.update();
                    break;
                case 5:
                    addressFacade.delete();
                    break;

                default:
                    System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
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
            indexes.add("Find By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            System.out.print(MetaData.SELECT_ONE_OPTION);
            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());

            switch (option) {
                case 0:
                    System.out.println(MetaData.EXITING_FROM_PROCESS);
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
                    studentFacade.findByStudentIdWithCourseName();
                    break;
                case 5:
                    studentFacade.update();
                    break;
                case 6:
                    studentFacade.delete();
                    break;

                default:
                    System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
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
            indexes.add("Update");
            indexes.add("Delete");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            System.out.print(MetaData.SELECT_ONE_OPTION);
            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());

            switch (option) {
                case 0:
                    System.out.println(MetaData.EXITING_FROM_PROCESS);
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
                    courseFacade.update();
                    break;
                case 5:
                    courseFacade.delete();
                    break;

                default:
                    System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
            }
        }
    }

    static void examResultScenario() {
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

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            System.out.print(MetaData.SELECT_ONE_OPTION);
            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());

            switch (option) {
                case 0:
                    System.out.println(MetaData.EXITING_FROM_PROCESS);
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
                    System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
            }
        }
    }

    static void updateORMSetting() {
        System.out.println("JPA is selected to use as Default ORM tool");
        System.out.println("Current using ORM Tool is : " + ORMConfigSingleton.getCurrentORMName());

        List<String> indexes = new ArrayList<>();
        indexes.add("JPA");
        indexes.add("Hibernate");
        String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
        System.out.println(msg);

        int option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());
        switch (option) {
            case 0:
                System.out.println(MetaData.EXITING_FROM_PROCESS);
                break;
            case 1:
                ORMConfigSingleton.enableJPA();
                resetORMServices();
                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            case 2:
                ORMConfigSingleton.enableHibernate();
                resetORMServices();
                System.out.println(ORMConfigSingleton.getCurrentORMName() + " is activated : ");
                break;
            default:
                System.out.println(MetaData.SWITCH_DEFAULT_INVALID_CHOICE);
        }

    }

    static  void resetORMServices(){
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(),addressFacade,courseFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getStudentService(), orm.getCourseService(),studentFacade);
    }
}