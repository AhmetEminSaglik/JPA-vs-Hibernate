package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.exceptions.InvalidCourseDeleteRequestStudentEnrolled;
import org.aes.compare.orm.model.Student;
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
    private StudentFacade studentFacade;

    public CourseFacade(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
//        if (studentFacade == null) {
        this.studentFacade = studentFacade;
//        }else{
//            System.out.println(ColorfulTextDesign.getErrorColorText("StudentFacade is already created."));
//        }
    }

    public Course save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);
        List<Course> properCourses = getProperCoursesToSave();
//        StringBuilder sbCourses = new StringBuilder();
//        sbCourses.append(createMsgFromListForCourses(properCourses))
//                .append("Select one option");
//        int result = SafeScannerInput.getCertainIntForSwitch(sbCourses.toString(), 0, properCourses.size() + 1);
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, properCourses);
        result--;
        Course course = properCourses.get(0);
        if (result == -1) {
            /*System.out.println("Course Save " + MetaData.PROCESS_IS_CANCELLED);
            System.out.println("Exiting from Course Save process...");*/
            FacadeUtility.destroyProcessCancelled();
//        } else if (result == properCourses.size() - 1) {
        } else {
            if (properCourses.get(result).getClass().getSimpleName().equals(OtherCourse.class.getSimpleName())) {
            course = properCourses.get(result);

            System.out.print("Type for Course name : ");
            String name = SafeScannerInput.getStringNotBlank();
            course.setName(name);

            System.out.print("Type for Course Credit (double): ");
            double credit = SafeScannerInput.getCertainDoubleSafe(1, 20);

            course.setcredit(credit);
            System.out.println("Course is saving...");
            courseService.save(course);

        } else {
            course = properCourses.get(result);
            System.out.println("Course is saving...");
            courseService.save(course);
        }
            FacadeUtility.destroyProcessSuccessfully();
            FacadeUtility.printSuccessResult(course.toString());
        }
        FacadeUtility.printSlash();

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
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyCourseSaved("")) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        System.out.println("All Courses are retrieved :");
        printArrWithNo(courses);

        FacadeUtility.printSlash();
        return courses;
    }

    public Course findByName() {
        return findByMultipleWay();
    }

    public Course findByMultipleWay() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);

        if (!isAnyCourseSaved("")) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.NOT_FOUND_ANY_SAVED_COURSE));
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcessCancelled(2);
//            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX + MetaData.NOT_FOUND_ANY_SAVED_COURSE));
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return null;
        }
        Course course = selectCourse(courses);
//        FacadeUtility.destroyProcessWithoutPrint(2);
//        if (course != null) {
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + course);
//        }

        FacadeUtility.printSlash();
        return course;
    }

    public List<Course> findAllCoursesBelongsToStudent() {
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            return null;
        }
        return findAllCoursesOfStudent("", student);
        /*if (student == null) {
            return null;
        }
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {

            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX+"Student has not been enrolled to any course yet."));
        } else {
            courses.forEach(System.out::println);
        }*/
//        return findAllCoursesOfStudent(student);
    }

    public List<Course> findAllCoursesOfStudent(String parentProcessName, Student student) {
//        Student student = studentFacade.findByMultipleWay();
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
//        String processPrefixName = parentProcessName + MetaData.INNER_PROCESS_PREFIX + MetaData.PROCESS_PREFIX_COURSE + MetaData.PROCESS_SELECT;
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcessCancelled( 2);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX + "Student has not been enrolled to any course yet."));
        } else {
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student's all courses(" + courses.size() + ") are retrieved.");
        }
        FacadeUtility.printSlash();
        return courses;
    }

  /*  public List<Course> printAllCoursesBelongsToStudent() {
        List<Course> courses = findAllCoursesBelongsToStudent();
        StringBuilder sb = FacadeUtility.createMsgFromListExit(courses);
        System.out.println(sb);

        return courses;
    }*/

    /*    public int getTotalCourseNumber() {
            return courseService.findAll().size();
        }*/
    public boolean isAnyCourseSaved(String parentProcessName) {
        int totalCourse = courseService.findAll().size();
        if (totalCourse == 0) {
//            if (parentProcessName != null && parentProcessName.length() > 0) {
//                parentProcessName += MetaData.INNER_PROCESS_PREFIX;
//            }
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(parentProcessName + MetaData.PROCESS_PREFIX_COURSE + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED));
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + MetaData.NOT_FOUND_ANY_SAVED_COURSE));
            FacadeUtility.destroyProcessCancelled();
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + MetaData.NOT_FOUND_ANY_SAVED_COURSE));
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return false;
        }
        return true;
    }


    private Course selectCourse(List<Course> courses) {
        Course course;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Course from List");
        indexes.add("Pick Course by typing course name");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_COURSE, indexes);

        switch (option) {
            case 0:
//                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.COURSE_NOT_SELECTED_PROCESS_CANCELLED));
//                // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
//                FacadeUtility.destroyProcessCancelled();
//                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + MetaData.COURSE_NOT_SELECTED));
                course = null;
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
                    System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + "Course is not found with given name(" + courseName + "). Please try again"));
                    return selectCourse(courses);
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return selectCourse(courses);
        }
        if (course == null) {
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcessCancelled();
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + MetaData.COURSE_NOT_SELECTED));
            FacadeUtility.printColorfulWarningResult(MetaData.COURSE_NOT_SELECTED);
        } else {
            // LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + course);
        }
        return course;

    }

    public Course pickCourseFromList(List<Course> courses) {
//        final String courseNotSelectedErr = "Course is not selected. Process is cancelled.";
        int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_COURSE, courses);
        selected--;
        if (selected == -1) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.COURSE_NOT_SELECTED_PROCESS_CANCELLED));
            return null;
        } else {
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.COURSE_SELECTED) + courses.get(selected));
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
        if (!isAnyCourseSaved("")) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        int selectedCourse = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_COURSE, courses);

        selectedCourse--;

        if (selectedCourse == -1) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
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
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
//                    courseService.updateCourseByName(course);
//                    System.out.println("Course is updated : " + course);
//                    System.out.println("Exiting Course Update Service");
                    return null;
                case 0:
                    courseService.updateCourseByName(course);
//                    System.out.println("Course is updated : " + course);
                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.COURSE_IS_UPDATED) + course);
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
        if (!isAnyCourseSaved("")) {
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
            try {
                courseService.deleteCourseById(course.getId());
                System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.COURSE_IS_DELETED));
            } catch (InvalidCourseDeleteRequestStudentEnrolled e) {
                System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(e.getMessage()));
            }
        }

    }

    private List<Course> getDefinedCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new OtherCourse("New Course", 0));
        courses.add(new MathCourse());
        courses.add(new ScienceCourse());
        courses.add(new LiteratureCourse());
        courses.add(new JavaCourse());
        courses.add(new FlutterCourse());
        courses.add(new ReactCourse());
        return courses;
    }


    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
//            System.out.println((i + 1) + "-) " + list.get(i));
            FacadeUtility.printSuccessResult((i + 1) + "-) " + list.get(i));
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
