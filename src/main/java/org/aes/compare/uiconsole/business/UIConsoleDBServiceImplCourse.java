package org.aes.compare.uiconsole.business;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.ORMApp;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.aes.compare.orm.model.courses.concretes.programming.ReactCourse;

import java.util.ArrayList;
import java.util.List;

public class UIConsoleDBServiceImplCourse  {
    private final List<Course> courses = new ArrayList<>();
    private final ORMApp ormApp = new ORMApp();

    public UIConsoleDBServiceImplCourse() {
        courses.add(new ScienceCourse());
        courses.add(new MathCourse());
        courses.add(new LiteratureCourse());
        courses.add(new JavaCourse());
        courses.add(new FlutterCourse());
        courses.add(new ReactCourse());
        courses.add(new OtherCourse());
    }

    private void addLoggerData() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_INNER_TERMINAL_PROCESS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
    }
    private  void destroyTerminalProcessSuccessfully(){
        FacadeUtility.destroyProcessSuccessfully(3);
    }
    /*//todo check here
    private void printCourseList() {
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + "-) " + courses.get(i).getName() + " (Credit:" + courses.get(i).getCredit() + ")");
            allCourseInString += (i + 1) + "-) " + courses.get(i).getName() + " (Credit:" + courses.get(i).getCredit() + "\n";
        }
    }*/

    public void create() {
        addLoggerData();
//        Course course =
        ormApp.getCourseFacade().save();
        destroyTerminalProcessSuccessfully();
//        return course;
    }

    public void read() {
        addLoggerData();
        List<Course> courses = ormApp.getCourseFacade().findAll();
        destroyTerminalProcessSuccessfully();
//        return courses;
    }


    public void update() {
        addLoggerData();
        ormApp.getCourseFacade().update();
        destroyTerminalProcessSuccessfully();
//        return course;
    }

    public void delete() {
        addLoggerData();
        ormApp.getCourseFacade().delete();
        destroyTerminalProcessSuccessfully();
    }
}
