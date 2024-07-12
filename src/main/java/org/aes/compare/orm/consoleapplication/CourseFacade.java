package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.model.Student;
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
            int credit = SafeScannerInput.getCertainIntSafe();

            course.setcredit(credit);
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
        System.out.print("Type Course Name : ");
        String name = SafeScannerInput.getStringNotBlank();
        Course course = courseService.findByName(name);
        if (course == null) {
            System.out.println("Course is not found : ");
        } else {
            System.out.println("Found course : " + course);
        }

        return course;
    }

    public Course update() {
        List<Course> courses = courseService.findAll();
        StringBuilder msg = new StringBuilder("Select one Course's index by given list:\n");
        msg.append(createMsgFromList(courses));

        int selectedCourse = SafeScannerInput.getCertainIntForSwitch(msg.toString(), 1, courses.size() + 1);
        selectedCourse--;

        if (selectedCourse == courses.size()) {
            System.out.println("Update Process is Cancelled.");
            return null;
        }

        Course course = courses.get(selectedCourse);
        int option = -1;

        StringBuilder sbProcess = new StringBuilder();
        sbProcess.append("1-) Update Course Name\n");
        sbProcess.append("2-) Update Course Credit\n");
        sbProcess.append("3-) Save And Exit\n");
        sbProcess.append("Select process No");

        while (option != 3) {
            System.out.println("Course : " + courses);
            option = SafeScannerInput.getCertainIntForSwitch(sbProcess.toString(), 1, 3);
            switch (option) {
                case 1:
                    System.out.print("Type Course new Name");
                    String name = SafeScannerInput.getStringNotBlank();
                    course.setName(name);
                    break;
                case 2:
                    System.out.print("Type Course new Credit (int)");
                    int credit = SafeScannerInput.getCertainIntSafe();
                    course.setcredit(credit);
                    break;
                case 3:
                    System.out.println("Course is updating...");
                    courseService.updateCourseByName(course);
                    System.out.println("Course is updated : " + course);
                    System.out.println("Exiting Course Update Service");
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

        return course;
    }

    public void delete() {
        List<Course> courses = courseService.findAll();

        StringBuilder sbMsg = new StringBuilder("Select Course number to delete.\n");
        sbMsg.append(createMsgFromList(courses));
        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 1, courses.size() + 1);
        result--;
        if (result == courses.size()) {
            System.out.println("Student Delete process is Cancelled");
        } else {
            Course course = courses.get(result);
            System.out.println("Course is deleting...");
            courseService.deleteCourseById(course.getId());
            System.out.println("Course is deleted");
        }

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
