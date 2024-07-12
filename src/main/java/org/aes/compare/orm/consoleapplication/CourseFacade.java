package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.aes.compare.orm.model.courses.concretes.programming.ReactCourse;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;

public class CourseFacade {
    private final CourseService courseService;

    public CourseFacade(CourseService courseService) {
        this.courseService = courseService;
    }

    public Course save(){
        List<Course> properCourses = getProperCoursesToSave();
        StringBuilder sbCourses = new StringBuilder();
        sbCourses.append(createMsgFromListForCourses(properCourses))
                .append("Select one option");
        int result = SafeScannerInput.getCertainIntForSwitch(sbCourses.toString(), 0, properCourses.size() + 1);
        result--;
        Course course = properCourses.get(0);
        if (result == properCourses.size()) {
            System.out.println("Course Save process is Cancelled.");
            System.out.println("Exiting from Course Save process...");
        } else if (result == properCourses.size() - 1) {
            course = properCourses.get(result);

            System.out.print("Type for Course name : ");
            String name = SafeScannerInput.getStringNotBlank();
            course.setName(name);

            System.out.print("Type for Course Credit (int): ");
            int credit = SafeScannerInput.getIntRecursive();

            course.setCredits(credit);
            System.out.println("Course is saving...");
            courseService.save(course);

        } else {
            course = properCourses.get(result);
            System.out.println("Course is saving...");
            courseService.save(course);
        }
//        courseService.save(course);
        System.out.println("Course is saved : " + course);

        return course;
    }

    private List<Course> getProperCoursesToSave() {
        List<Course> registeredCourses = courseService.findAll();
        List<Course> definedCoursesAsObject = getDefinedCourses();
//        System.out.println("registered courses : ");
//        registeredCourses.forEach(System.out::println);
//        System.out.println("defined courses : ");
//        definedCoursesAsObject.forEach(System.out::println);


        for (int i = 0; i < registeredCourses.size(); i++) {
            for (int j = 0; j < definedCoursesAsObject.size(); j++) {
                if (registeredCourses.get(i).getName().equals(definedCoursesAsObject.get(j).getName())) {
                    definedCoursesAsObject.remove(j);
                    j--;
                }

            }
        }
        return definedCoursesAsObject;
    }

    public List<Course> findAll() {
        List<Course> courses = courseService.findAll();
        System.out.println("All Courses are retrieved :");
        printArrWithNo(courses);
        System.out.println("-------------------------");
        return courses;
    }

    public Course findByName() {
        return null;
    }

    public Course update() {
        return null;
    }

    public void delete() {
    }

    private List<Course> getDefinedCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new MathCourse());
        courses.add(new ScienceCourse());
        courses.add(new LiteratureCourse());
        courses.add(new JavaCourse());
        courses.add(new FlutterCourse());
        courses.add(new ReactCourse());
        courses.add(new OtherCourse("New Course", 0));
        return courses;
    }


    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
    }

    private StringBuilder createMsgFromListForCourses(List<Course> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1) + "-) " + list.get(i).getName() + "\n");
        }
        sb.append((list.size() + 1) + "-) Exit/Cancel");
        return sb;
    }

    private StringBuilder createMsgFromList(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1) + "-) " + list.get(i) + "\n");
        }
        sb.append((list.size() + 1) + "-) Exit/Cancel");
        return sb;
    }

}
