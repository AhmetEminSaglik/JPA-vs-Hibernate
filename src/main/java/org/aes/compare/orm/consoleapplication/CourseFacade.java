package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
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

public class CourseFacade {
    private final CourseService courseService;

    public CourseFacade(CourseService courseService) {
        this.courseService = courseService;
    }

    public Course save(){
        List<Course> properCourses = getProperCoursesToSave();
//        StringBuilder sbCourses = new StringBuilder();
//        sbCourses.append(createMsgFromListForCourses(properCourses))
//                .append("Select one option");
//        int result = SafeScannerInput.getCertainIntForSwitch(sbCourses.toString(), 0, properCourses.size() + 1);
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, properCourses);
        result--;
        Course course = properCourses.get(0);
        if (result == -1) {
            System.out.println("Course Save " + MetaData.PROCESS_IS_CANCELLED);
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
        System.out.println(MetaData.COURSE_IS_SAVED + course);

        return course;
    }

    private List<Course> getProperCoursesToSave() {
        List<Course> registeredCourses = courseService.findAll();
        List<Course> definedCoursesAsObject = getDefinedCourses();
//        System.out.println("registered courses : ");
//        registeredCourses.forEach(System.out::println);
//        System.out.println("defined courses : ");
//        definedCoursesAsObject.forEach(System.out::println);


        for (Course registeredCours : registeredCourses) {
            for (int j = 0; j < definedCoursesAsObject.size(); j++) {
                if (registeredCours.getName().equals(definedCoursesAsObject.get(j).getName())) {
                    definedCoursesAsObject.remove(j);
                    j--;
                }

            }
        }
        return definedCoursesAsObject;
    }

    public List<Course> findAll() {
        if (!isAnyCourseSaved()) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        System.out.println("All Courses are retrieved :");
        printArrWithNo(courses);
        System.out.println("-------------------------");
        return courses;
    }

    public Course findByName() {
        if (!isAnyCourseSaved()) {
            return null;
        }
        return findByMultipleWay();
//        System.out.print("Type Course Name : ");
//        String name = SafeScannerInput.getStringNotBlank();
//        Course course = courseService.findByName(name);
//        if (course == null) {
//            System.out.println("Course is not found : ");
//        } else {
//            System.out.println("Found course : " + course);
//        }
//        return course;
    }

    /*    public int getTotalCourseNumber() {
            return courseService.findAll().size();
        }*/
    public boolean isAnyCourseSaved() {
        int totalCourse = courseService.findAll().size();
        if (totalCourse == 0) {
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return false;
        }
        return true;
    }

    public Course findByMultipleWay() {
        List<Course> courses=courseService.findAll();
        if(courses.isEmpty()){
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return null;
        }
        Course course = null;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Course from List");
        indexes.add("Pick Course by typing course name");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_COURSE, indexes);

        switch (option) {
            case 0:
                System.out.println(MetaData.COURSE_NOT_SELECTED_PROCESS_CANCELED);
                break;
            case 1:
                course = pickCourseFromList(courses);
                /*if (course == null) {
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(courseNotSelectedErr));
                } else {
                    System.out.println(ColorfulTextDesign.getSuccessColorText("Selected Course : ") + course);
                }*/
                break;
            case 2:
                System.out.print("Type Course Name : ");
                String courseName = SafeScannerInput.getStringNotBlank();
                course = courseService.findByName(courseName);
                if (course == null) {
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Course is not found with given name(" + courseName + "). Please try again"));
                    return findByMultipleWay();
                } else {
                    System.out.println(ColorfulTextDesign.getSuccessColorText("Selected Course : ") + courseName);
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return findByMultipleWay();
        }
        return course;
    }

    public Course pickCourseFromList(List<Course> courses) {
//        final String courseNotSelectedErr = "Course is not selected. Process is cancelled.";
        int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_COURSE, courses);
        selected--;
        if (selected == -1) {
            System.out.println(MetaData.COURSE_NOT_SELECTED_PROCESS_CANCELED);
            return null;
        } else {
            System.out.println(MetaData.COURSE_SELECTED + courses);
            return courses.get(selected);
        }
        /*
        StringBuilder sb = createMsgFromList(courses);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, courses.size());
        index--;
        if (index == -1) {
            return null;
        }
        return students.get(index);
    */
    }

    public Course update() {
        if (!isAnyCourseSaved()) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        int selectedCourse = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, courses);

        selectedCourse--;

        if (selectedCourse == -1) {
            System.out.println(MetaData.PROCESS_IS_CANCELLED);
            return null;
        }
        Course course = courses.get(selectedCourse);
        return updateSpecificCourse(course);
    }

    private Course updateSpecificCourse(Course course) {
        int option = Integer.MAX_VALUE;

        List<String> indexed = new ArrayList<>();
        indexed.add("Update Course Name");
        indexed.add("Update Course Credit");

        while (option != -1 && option != -2) {
            System.out.println("Current Course : " + course);
            option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_COURSE, indexed);
//            option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 1, 3);
            switch (option) {
                case -1:
                    System.out.println(MetaData.PROCESS_IS_CANCELLED);
//                    courseService.updateCourseByName(course);
//                    System.out.println("Course is updated : " + course);
//                    System.out.println("Exiting Course Update Service");
                    return null;
                case 0:
                    courseService.updateCourseByName(course);
//                    System.out.println("Course is updated : " + course);
                    System.out.println(MetaData.COURSE_IS_UPDATED + course);
                    return course;
//                    System.out.println("Exiting Course Update Service");
                case 1:
                    System.out.print("Type Course new Name: ");
                    String name = SafeScannerInput.getStringNotBlank();
                    course.setName(name);
                    break;
                case 2:
                    System.out.print("Type Course new Credit (double): ");
                    double credit = SafeScannerInput.getCertainDoubleSafe(1, 20);
                    course.setcredit(credit);
                    break;

                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

        return course;

    }

    public void delete() {
        if (!isAnyCourseSaved()) {
            return;
        }
        /*List<Course> courses = courseService.findAll();

        StringBuilder sbMsg = new StringBuilder("Select Course number to delete.\n");
        sbMsg.append(createMsgFromList(courses));
        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 1, courses.size() + 1);*/
        List<Course> courses = courseService.findAll();

        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, courses);


        result--;
        if (result == courses.size()) {
            System.out.println("Course Delete process is Cancelled");
        } else {
            Course course = courses.get(result);
            System.out.println("Course is deleting...");
            courseService.deleteCourseById(course.getId());
            System.out.println(MetaData.COURSE_IS_DELETED);
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
