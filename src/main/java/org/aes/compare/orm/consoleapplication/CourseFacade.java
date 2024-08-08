package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.InnerTerminalProcessLayout;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.exceptions.InvalidCourseDeleteRequestStudentEnrolled;
import org.aes.compare.orm.exceptions.InvalidCourseNameSaveRequestException;
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

import java.util.ArrayList;
import java.util.List;

public class CourseFacade extends TerminalCommandLayout {
    private final CourseService courseService;
    private StudentFacade studentFacade;

    public CourseFacade(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    public void save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);
        saveCourseProcess();
    }

    private void saveCourseProcess() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        Course course;
        while (interlayout.isAllowedCurrentProcess()) {
            List<Course> properCourses = getProperCoursesToSave();

            int result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, properCourses);

            if (FacadeUtility.isCancelledProcess(interlayout)) {
                FacadeUtility.destroyProcessCancelled();
                FacadeUtility.printSlash();
                return;
            }
            if (result == 0) {
                FacadeUtility.destroyProcessExiting();
                FacadeUtility.printSlash();
                return;
            }

            result--;
            if (properCourses.get(result).getClass().getSimpleName().equals(OtherCourse.class.getSimpleName())) {
                course = properCourses.get(result);

                String title = "Type for Course name : ";
                String name = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
                    FacadeUtility.destroyProcessCancelled();
                    FacadeUtility.printSlash();
                    return;
                }

                course.setName(name);

                title = "Type for Course Credit (double): ";
                double credit = FacadeUtility.getSafeDoubleInputFromTerminalProcess(interlayout, title, 1, 20);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
                    FacadeUtility.destroyProcessCancelled(1);
                    FacadeUtility.printSlash();
                    return;
                }
                course.setCredit(credit);
                courseService.save(course);
            } else {
                course = properCourses.get(result);
                courseService.save(course);
            }
            FacadeUtility.destroyProcessSuccessfully(1);
            FacadeUtility.printSuccessResult(course.toString());

            FacadeUtility.printSlash();
        }

    }

    private List<Course> getProperCoursesToSave() {
        List<Course> registeredCourses = courseService.findAll();
        List<Course> definedCoursesAsObject = getDefinedCourses();

        for (Course registeredCourse : registeredCourses) {
            for (int j = 0; j < definedCoursesAsObject.size(); j++) {
                if (registeredCourse.getName().equals(definedCoursesAsObject.get(j).getName())) {
                    definedCoursesAsObject.remove(j);
                    j--;
                }
            }
        }
        return definedCoursesAsObject;
    }

    public List<Course> findAll() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyCourseSaved()) {
            FacadeUtility.printSlash();
            return null;
        }
        List<Course> courses = courseService.findAll();
        System.out.println("All Courses are retrieved :");
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(courses);
        FacadeUtility.printSlash();
        return courses;
    }

    public Course findByName() {
        return findByMultipleWay();
    }

    public Course findByMultipleWay() {
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);

        if (!isAnyCourseSaved()) {
            FacadeUtility.printSlash();
            return null;
        }
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled(2);
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            FacadeUtility.printSlash();
            return null;
        }
        Course course = selectCourse(courses);
        if (course == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult(MetaData.COURSE_NOT_SELECTED);
        } else {
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + course);
        }

        FacadeUtility.printSlash();
        return course;
    }

    public List<Course> findAllCoursesBelongsToStudent() {
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
//            FacadeUtility.printSlash();
            return null;
        }
        return findAllCoursesOfStudent(student);
    }

    public List<Course> findAllCoursesOfStudent(Student student) {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled(2);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX + "Student has not been enrolled to any course yet."));
        } else {
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student's all courses(" + courses.size() + ") are retrieved.");
            if (!courses.isEmpty()) {
                FacadeUtility.printArrResult(courses);
            }
        }
        FacadeUtility.printSlash();
        return courses;
    }

    public boolean isAnyCourseSaved() {
        int totalCourse = courseService.findAll().size();
        if (totalCourse == 0) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return false;
        }
        return true;
    }


    private Course selectCourse(List<Course> courses) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        Course course;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Course from List");
        indexes.add("Pick Course by typing course name");

        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
//            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        switch (option) {
            case 0:
                return null;
            case 1:
                return pickCourseFromList(courses);
            case 2:
                String title = "Type Course Name : ";
                String courseName = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
//                    FacadeUtility.destroyProcessCancelled(2);
                    return null;
                }
                course = courseService.findByName(courseName);
                if (course == null) {
                    System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + "Course is not found with given name(" + courseName + "). Please try again"));
                    return selectCourse(courses);
                }
                return course;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return selectCourse(courses);
        }


    }

    public Course pickCourseFromList(List<Course> courses) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        int selected = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, courses);
        if (FacadeUtility.isCancelledProcess(interlayout) || selected == 0) {
            return null;
        }
        return courses.get(--selected);
    }

    public void update() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);

        if (!isAnyCourseSaved()) {
            FacadeUtility.printSlash();
            return;
        }
        List<Course> courses = courseService.findAll();

        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);

        int selectedCourse = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, courses);
        if (FacadeUtility.isCancelledProcess(interlayout) || selectedCourse == 0) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printSlash();
            return;
        }
        FacadeUtility.destroyProcessSuccessfully();
        selectedCourse--;

        Course course = courses.get(selectedCourse);
        updateSelectedCourse(courses, course);
        FacadeUtility.printSlash();

    }

    private Course updateSelectedCourse(List<Course> courses, Course course) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
//        int option = Integer.MAX_VALUE;
        Course tmpCourse = new OtherCourse(course);
        List<Course> tmpCourses = new ArrayList<>(List.copyOf(courses));
        tmpCourses.remove(course);
        List<String> indexed = new ArrayList<>();
        indexed.add("Update Course Name");
        indexed.add("Update Course Credit");
        while (interlayout.isAllowedCurrentProcess()) {
            String title;
            FacadeUtility.printSuccessResult("Current Course : " + tmpCourse);
            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(interlayout, indexed);
            if (FacadeUtility.isCancelledProcess(interlayout)) {
                FacadeUtility.destroyProcessCancelled();
                return null;
            }
            switch (option) {
                case -1:
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                case 0:
                    try {
                        for (Course tmp : tmpCourses) {
                            if (tmp.getName().equalsIgnoreCase(tmpCourse.getName())) {
                                throw new InvalidCourseNameSaveRequestException(tmpCourse.getName());
                            }
                        }
                        course.setName(tmpCourse.getName());
                        course.setCredit(tmpCourse.getCredit());

                        courseService.updateCourseByName(course);
                        FacadeUtility.destroyProcessSuccessfully();
                        FacadeUtility.printSuccessResult("Course : " + course);
                        return course;
                    } catch (InvalidCourseNameSaveRequestException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 1:
                    title = "Type Course new Name: ";
//                    String name = SafeScannerInput.getStringNotBlank(interlayout);
                    String name = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }

                    tmpCourse.setName(name);
                    break;
                case 2:
                    title = "Type Course new Credit (double): ";
//                    double credit = SafeScannerInput.getCertainDoubleSafe(interlayout, 1, 20);
                    double credit = FacadeUtility.getSafeDoubleInputFromTerminalProcess(interlayout, title, 1, 20);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    tmpCourse.setCredit(credit);
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }
        return null;
    }

    public void delete() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyCourseSaved()) {
            FacadeUtility.printSlash();
            return;
        }
        while (interlayout.isAllowedCurrentProcess()) {
        List<Course> courses = courseService.findAll();
            if (courses.isEmpty()) {
                FacadeUtility.destroyProcessExiting(2);
                FacadeUtility.printColorfulWarningResult("Not remained any deletable course.");
                FacadeUtility.printSlash();
                return;
            }

        int result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, courses);
        if (FacadeUtility.isCancelledProcess(interlayout) || result == 0) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printSlash();
            return;
        }
        result--;
        Course course = courses.get(result);
        try {
            courseService.deleteCourseById(course.getId());
            FacadeUtility.destroyProcessSuccessfully(1);
            FacadeUtility.printSuccessResult("Course (name=" + course.getName() + ") is deleted.");
        } catch (InvalidCourseDeleteRequestStudentEnrolled e) {
            System.out.println(e.getMessage());
            FacadeUtility.printColorfulWarningResult("To delete this course first remove all students who take this course.");
        }
            FacadeUtility.printSlash();
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
}
