package org.aes.compare.orm;

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
    static ORMConfigSingleton orm = new ORMConfigSingleton();
    static AddressFacade addressFacade;// = new addressFacadeFacade(orm.getAddressService());
    static StudentFacade studentFacade;// = new studentFacadeFacade(orm.getStudentService(), orm.getAddressService());
    static CourseFacade courseFacade;
    static ExamResultFacade examResultFacade;

    private final static String selectOptionText = "Select one of the option";

    public static void main(String[] args) {
        ORMConfigSingleton.enableJPA();
//        ORMConfigSingleton.enableHibernate();
        addressFacade = new AddressFacade(orm.getAddressService());
        courseFacade = new CourseFacade(orm.getCourseService());
        studentFacade = new StudentFacade(orm.getStudentService(),addressFacade,courseFacade);
        examResultFacade = new ExamResultFacade(orm.getExamResultService(), orm.getStudentService(), orm.getCourseService(),studentFacade);

        int globalOption = -1;
        while (globalOption != 5) {

            List<String> indexes = new ArrayList<>();
            indexes.add("Address");
            indexes.add("Student");
            indexes.add("Course");
            indexes.add("ExamResult");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            globalOption = SafeScannerInput.getCertainIntForSwitch(selectOptionText, 1, 5);

            switch (globalOption) {
                case 1:
                    System.out.println("Leads to Address Process");
                    addressScenario();
                    break;
                case 2:
                    System.out.println("Leads to Student Process");
                    studentScenario();
                    break;
                case 3:
                    System.out.println("Leads to Course Process");
                    courseScenario();
                    break;
                case 4:
                    System.out.println("Leads to Exam Result Process");
                    examResultScenario();
                    break;
                case 5:
                    System.out.println("Exitting the program");
                    break;
                default:
                    System.out.println("Invalid choose please try again!");
            }
        }

    }

    static void addressScenario() {
        int option = -1;
        while (option != 6) {

            List<String> indexes = new ArrayList<>();
            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By Id");
            indexes.add("Update");
            indexes.add("Delete");
//            indexes.add("Exit");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            option = SafeScannerInput.getCertainIntForSwitch(selectOptionText, 1, 6);

            switch (option) {
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
                case 6:
                    System.out.println("exitting the address Service");
                    break;
                default:
                    System.out.println("Invalid Choose try again");
            }
        }
    }

    static void studentScenario() {
        int option = -1;
        while (option != 7) {

            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By Id");
            indexes.add("Find By Student Id And Course Name");
            indexes.add("Update");
            indexes.add("Delete");
//            indexes.add("Exit");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);


            option = SafeScannerInput.getCertainIntForSwitch(selectOptionText, 1, 7);

            switch (option) {
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
                case 7:
                    System.out.println("Exiting the Student Service");
                    break;
                default:
                    System.out.println("Invalid Choose try again");
            }
        }
    }

    static void courseScenario() {
        int option = -1;
        while (option != 6) {

            List<String> indexes = new ArrayList<>();


            indexes.add("Exit");
            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find By Name");
            indexes.add("Update");
            indexes.add("Delete");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            option = SafeScannerInput.getCertainIntForSwitch(selectOptionText, 1, 6);

            switch (option) {
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
                case 6:
                    System.out.println("Exiting the Course Service");
                    break;
                default:
                    System.out.println("Invalid Choose try again");
            }
        }
    }

    static void examResultScenario() {
        int option = -1;
        while (option != 8) {

            List<String> indexes = new ArrayList<>();

            indexes.add("Save");
            indexes.add("Find All");
            indexes.add("Find All By Student (Id)");
            indexes.add("Find All By Course (Name)");
            indexes.add("Find All By Student (Id) And Course (Name)");
            indexes.add("Update");
            indexes.add("Delete");
            indexes.add("Exit");

            String msg = FacadeUtility.createMsgFromListExit(indexes).toString();
            System.out.println(msg);

            option = SafeScannerInput.getCertainIntForSwitch(selectOptionText, 1, 6);

            switch (option) {
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
                case 8:
                    System.out.println("Exiting the Exam Result Service");
                    break;
                default:
                    System.out.println("Invalid Choose try again");
            }
        }
    }

}