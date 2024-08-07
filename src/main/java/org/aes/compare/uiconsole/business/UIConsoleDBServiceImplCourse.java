package org.aes.compare.uiconsole.business;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandProcessCheck;
import org.aes.compare.customterminal.business.concretes.TerminalCommandManager;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.aes.compare.orm.model.courses.concretes.programming.ReactCourse;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;

public class UIConsoleDBServiceImplCourse implements TerminalCommandProcessCheck {
    private final TerminalCommandManager tcm;
    private final TerminalCommandLayout layout;
    private final ORMConfigSingleton ormConfig = new ORMConfigSingleton();
    private final List<Course> courses = new ArrayList<>();
    private String allCourseInString = "";

    public UIConsoleDBServiceImplCourse(TerminalCommandManager tcm, TerminalCommandLayout layout) {
        this.tcm = tcm;
        this.layout = layout;
        courses.add(new ScienceCourse());
        courses.add(new MathCourse());
        courses.add(new LiteratureCourse());
        courses.add(new JavaCourse());
        courses.add(new FlutterCourse());
        courses.add(new ReactCourse());
        courses.add(new OtherCourse());
    }

    private void printCourseList() {
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + "-) " + courses.get(i).getName() + " (Credit:" + courses.get(i).getCredit() + ")");
            allCourseInString += (i + 1) + "-) " + courses.get(i).getName() + " (Credit:" + courses.get(i).getCredit() + "\n";
        }
    }

    public void save() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE SAVE COURSE"));
        /*

        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("--> Course Save process is initialized"));
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix("Each course's name must be unique"));
        String msg = "Choose one of following course number :";
        printCourseList();
        int selected = SafeScannerInput.getIntInput(0, courses.size() - 1, msg + allCourseInString, tcm);
        if (selected < courses.size() - 1) {
            ormConfig.getCourseService().save(courses.get(selected));
        } else {

            String inputMsg = ColorfulTextDesign.getTextForUserFeedback("Type Course Name: ");
            String stringInput = SafeScannerInput.getStringInput(inputMsg, tcm);
            Course course = new OtherCourse();
//            course.setName();
        }


       *//* if (isCanceled()) return;
        address.setCity(stringInput);

        inputMsg = ColorfulTextDesign.getTextForUserFeedback("Type Street (String):");
        stringInput = SafeScannerInput.getStringInput(inputMsg, tcm);
        if (isCanceled()) return;
        address.setStreet(stringInput);


        inputMsg = ColorfulTextDesign.getTextForUserFeedback("Type Country (String):");

        stringInput = SafeScannerInput.getStringInput(inputMsg, tcm);
        if (isCanceled()) return;
        address.setCountry(stringInput);

        ormConfig.getAddressService().save(address);

        System.out.println(ColorfulTextDesign.getSuccessColorTextWithPrefix("Address is saved: " + address));*//*

        //        ormConfig.getAddressService().save(address);

//        ormConfig.getCourseService().save(c);
    */}

    public void findByName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findByName  COURSE"));
//        return ormConfig.getCourseService().findByName(name);
    }


    public void findAll() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAll  COURSE"));
        // return ormConfig.getCourseService().findAll();
    }


    public void updateCourseByName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE updateCourseByName  COURSE"));
        //   ormConfig.getCourseService().updateCourseByName(course);
    }


    public void deleteCourseByName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE deleteCourseByName  COURSE"));
        //  ormConfig.getCourseService().deleteCourseByName(name);
    }


    public void deleteCourseById() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE deleteCourseById  COURSE"));
        //  ormConfig.getCourseService().deleteCourseById(id);
    }


    public void resetTable() {
        //  ormConfig.getCourseService().resetTable();
    }

    @Override
    public boolean isCanceled() {
        return tcm.isAllowedCurrentProcess();
    }
}
