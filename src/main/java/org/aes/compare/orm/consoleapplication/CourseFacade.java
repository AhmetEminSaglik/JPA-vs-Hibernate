package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
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
import org.aes.compare.uiconsole.utility.SafeScannerInput;

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

    public Course save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);
        List<Course> properCourses = getProperCoursesToSave();
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(this,MetaData.PROCESS_PREFIX_COURSE, properCourses);
        result--;
        Course course = properCourses.get(0);
        if (result == -1) {
            FacadeUtility.destroyProcessCancelled();
        } else {
            if (properCourses.get(result).getClass().getSimpleName().equals(OtherCourse.class.getSimpleName())) {
            course = properCourses.get(result);

            System.out.print("Type for Course name : ");
            String name = SafeScannerInput.getStringNotBlank();
            course.setName(name);

            System.out.print("Type for Course Credit (double): ");
            double credit = SafeScannerInput.getCertainDoubleSafe(1, 20);

                course.setCredit(credit);
            courseService.save(course);
        } else {
            course = properCourses.get(result);
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
        if (!isAnyCourseSaved()) {
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
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);

        if (!isAnyCourseSaved()) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled(2);
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return null;
        }
        Course course = selectCourse(courses);

        FacadeUtility.printSlash();
        return course;
    }

    public List<Course> findAllCoursesBelongsToStudent() {
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            return null;
        }
        return findAllCoursesOfStudent("", student);
    }

    public List<Course> findAllCoursesOfStudent(String parentProcessName, Student student) {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled( 2);
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
        Course course;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Course from List");
        indexes.add("Pick Course by typing course name");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(this,MetaData.PROCESS_PREFIX_COURSE, indexes);

        switch (option) {
            case 0:
                course = null;
                break;
            case 1:
                course = pickCourseFromList(courses);
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
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult(MetaData.COURSE_NOT_SELECTED);
        } else {
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + course);
        }
        return course;
    }

    public Course pickCourseFromList(List<Course> courses) {
        int selected = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(this,MetaData.PROCESS_PREFIX_COURSE, courses);
        selected--;
        if (selected == -1) {
            return null;
        } else {
            return courses.get(selected);
        }
    }

    public Course update() {
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);

        if (!isAnyCourseSaved()) {
            return null;
        }
        List<Course> courses = courseService.findAll();
        int selectedCourse = FacadeUtility.getIndexValueOfMsgListIncludesExit(this,MetaData.PROCESS_PREFIX_COURSE, courses);

        selectedCourse--;

        if (selectedCourse == -1) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        Course course = courses.get(selectedCourse);
        return updateSelectedCourse(courses, course);
    }

    private Course updateSelectedCourse(List<Course> courses, Course course) {
//        int option = Integer.MAX_VALUE;
        Course tmpCourse = new OtherCourse(course);

        List<String> indexed = new ArrayList<>();
        indexed.add("Update Course Name");
        indexed.add("Update Course Credit");

        while (true) {
            System.out.println("Current Course : " + tmpCourse);
            int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(this,MetaData.PROCESS_PREFIX_COURSE, indexed);
            switch (option) {
                case -1:
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                case 0:
                    try {
                        for (Course tmp : courses) {
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
                    System.out.print("Type Course new Name: ");
                    String name = SafeScannerInput.getStringNotBlank();
                    tmpCourse.setName(name);
                    break;
                case 2:
                    System.out.print("Type Course new Credit (double): ");
                    double credit = SafeScannerInput.getCertainDoubleSafe(1, 20);
                    tmpCourse.setCredit(credit);
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }
    }

    public void delete() {
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyCourseSaved()) {
            return;
        }
        List<Course> courses = courseService.findAll();
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(this,MetaData.PROCESS_PREFIX_COURSE, courses);
        result--;
        if (result == -1) {
            FacadeUtility.destroyProcessCancelled();
        } else {
            Course course = courses.get(result);
            try {
                courseService.deleteCourseById(course.getId());
                FacadeUtility.destroyProcessSuccessfully();
                FacadeUtility.printSuccessResult("Course (name=" + course.getName() + ") is deleted.");
            } catch (InvalidCourseDeleteRequestStudentEnrolled e) {
                System.out.println(e.getMessage());
                FacadeUtility.destroyProcessCancelled();
                FacadeUtility.printColorfulWarningResult("To delete this course first remove all students who take this course.");
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
}
