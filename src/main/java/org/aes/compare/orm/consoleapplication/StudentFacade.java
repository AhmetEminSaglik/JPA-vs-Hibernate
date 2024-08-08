package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.InnerTerminalProcessLayout;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.LoggerProcessStack;

import java.util.ArrayList;
import java.util.List;

public class StudentFacade extends TerminalCommandLayout {
    private final StudentService studentService;
    private final AddressFacade addressFacade;
    private final CourseFacade courseFacade;
    private final CourseService courseService;

    public StudentFacade(StudentService studentService, AddressFacade addressFacade, CourseFacade courseFacade) {
        ORMConfigSingleton orm = new ORMConfigSingleton();
        this.studentService = studentService;
        this.addressFacade = addressFacade;
        this.courseFacade = courseFacade;
        this.courseService = orm.getCourseService();
    }

    public Student save() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);

        Student student = new Student();
        String title = "Type Student name: ";
        String name = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        student.setName(name);

        title = "Type Student grade: ";
        int grade = FacadeUtility.getSafeIntInputFromTerminalProcess(interlayout, title, 1, 6);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        student.setGrade(grade);

//        int grade = SafeScannerInput.getCertainIntSafe(interlayout, 1, 6);
//        student.setGrade(grade);

        Address address = studentSaveProcessDecideAddressProgress();
        if (address == null) {
            FacadeUtility.destroyProcessCancelled();
            System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + MetaData.STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED));
        } else {
            FacadeUtility.printSuccessResult("Selected Address " + address);
            student.setAddress(address);
            studentService.save(student);
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + student);
        }
        FacadeUtility.printSlash();
        return student;
    }

    private Address updateStudentAddressProgress(Address address) {
        return addressFacade.updateSelectedAddress(address);
    }

    private Address studentSaveProcessDecideAddressProgress() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
        Address address = addressFacade.pickAddressForStudentSaveProcess();
        FacadeUtility.destroyProcessWithoutPrint();
        return address;
    }

    public Student findByMultipleWay() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        Student student = selectStudent();
        FacadeUtility.printSlash();
        return student;
    }

    private Student selectStudent() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        Student student;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Student from List");
        indexes.add("Pick Student by typing Student id");
//        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(interlayout, MetaData.PROCESS_PREFIX_STUDENT, indexes);
        int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
        if (FacadeUtility.isCancelledProcess(interlayout)) {
            FacadeUtility.destroyProcessCancelled(2);
            return null;
        }
        switch (option) {
            case 0:
                student = null;
                break;
            case 1:
                student = pickStudentFromList(studentService.findAll());
                break;
            case 2:
                String title = "Type Student id (int): ";
                int id = FacadeUtility.getSafeIntInputFromTerminalProcess(interlayout, title);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                }
                student = studentService.findById(id);
                if (student == null) {
                    FacadeUtility.printColorfulErrorResult("Student is not found with given Id(" + id + "). Please try again.");
                    return selectStudent();
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return selectStudent();
        }
        if (student == null) {
            FacadeUtility.destroyProcessCancelled(2);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX + MetaData.STUDENT_NOT_SELECTED));
        } else {
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + student);
        }
        return student;
    }

    public List<Course> findStudentAllCourses() {
        Student student = findByMultipleWay();
        if (student == null) {
            return null;
        }
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        List<Course> courses = courseFacade.findAllCoursesOfStudent(MetaData.PROCESS_PREFIX_STUDENT, student);
        FacadeUtility.destroyProcessWithoutPrint();
        return courses;
    }

    public Student pickStudentFromList(List<Student> students) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        int index = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, students);
        if (FacadeUtility.isCancelledProcess(interlayout) || index == 0) {
            return null;
        }
        index--;
        return students.get(index);
    }

    public List<Student> findAll() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        List<Student> students = studentService.findAll();
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(students);
        FacadeUtility.printSlash();
        return students;
    }

    public int getTotalStudentNumber() {
        return studentService.findAll().size();
    }


    public boolean isAnyStudentSaved() {
        int totalStudent = getTotalStudentNumber();
        if (totalStudent == 0) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_STUDENT);
            return false;
        }
        return true;
    }


    public Student findByStudentIdWithCourseName() {
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()) {
            return null;
        }
        if (!isAnyStudentSaved()) {
            return null;
        }
        Student student = findByMultipleWay();
        if (student == null) {
            return null;
        }
        LoggerProcessStack.add(MetaData.PROCESS_READ);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        Course course = courseFacade.findByMultipleWay();
        System.out.println("gelen course ; " + course);
        if (course == null) {
            FacadeUtility.destroyProcessCancelled(4);
            return null;
        }
        try {
            FacadeUtility.destroyProcessWithoutPrint(2);
            student = studentService.findByStudentIdWithCourseName(student.getId(), course.getName());
            FacadeUtility.printSuccessResult("Requested Student : " + student);
            FacadeUtility.printSuccessResult("Requested Student's Course : " + course);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(e.getMessage()));
        }
        return student;
    }


    public Student update() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        List<Student> students = studentService.findAll();

        int selectedStudent = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, students);
        if (FacadeUtility.isCancelledProcess(interlayout) || selectedStudent == 0) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        selectedStudent--;

        Student student = students.get(selectedStudent);
        student = updateSelectedStudent(student);
        return student;
    }

    private Student updateSelectedStudent(Student student) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();

        List<String> indexes = new ArrayList<>();
        indexes.add("Update Student Name");
        indexes.add("Update Student Grade");
        indexes.add("Update Student Courses");
        indexes.add("Update Student Address");

        int option = -1;
        String title;
        while (interlayout.isAllowedCurrentProcess()) {
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student : " + student);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX) +
                    ColorfulTextDesign.getWarningColorText(MetaData.NOTE_PREFIX) +
                    "To complete current student's update process, you should select " +
                    ColorfulTextDesign.getWarningColorText("[Save & Exit]")
                    +
                    " option.");
            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(interlayout, indexes);
            if (FacadeUtility.isCancelledProcess(interlayout)) {
                FacadeUtility.destroyProcessCancelled();
                return null;
            }
            switch (option) {
                case -1:
                    FacadeUtility.destroyProcessCancelled();
                    return null;
                case 0:
                    studentService.update(student);
                    FacadeUtility.destroyProcessSuccessfully();
                    FacadeUtility.printSuccessResult("Student : " + student);
                    return student;

                case 1:
                    title = "Type Student new Name: ";
                    String name = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }

                    student.setName(name);
                    break;
                case 2:
                    title = "Type Student Grade(int): ";
                    int grade = FacadeUtility.getSafeIntInputFromTerminalProcess(interlayout, title, 1, 6);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
                        FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    student.setGrade(grade);
                    break;
                case 3:
                    if (courseFacade.isAnyCourseSaved()) {
                        List<Course> courses = updateStudentCourseProgress(student.getId());
                        if (courses != null) {
                            student.setCourses(courses);
                            studentService.update(student);
                            FacadeUtility.printSuccessResult(MetaData.STUDENT_COURSES_ARE_UPDATED);
                        }
                    } else {
                        LoggerProcessStack.add(MetaData.PROCESS_UPDATE);
                    }
                    break;
                case 4:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
                    updateStudentAddressProgress(student.getAddress());
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

        return student;
    }

    private List<Course> updateStudentCourseProgress(int studentId) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);

        List<Course> studentCourses = courseService.findAllCourseOfStudentId(studentId);
        List<Course> courseStudentDidNotEnroll = courseService.findAllCourseThatStudentDoesNotHave(studentId);

        int option = -1;
        while (interlayout.isAllowedCurrentProcess()) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Match course from registered Courses (" + courseStudentDidNotEnroll.size() + ")");
            indexes.add("Remove course from student Courses (" + studentCourses.size() + ")");
            indexes.add("Create new Course");

            printArrWithNo(studentCourses);
            option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistAndCancelFromTerminalProcess(interlayout, indexes);
            int result;
            if (FacadeUtility.isCancelledProcess(interlayout)) {
                FacadeUtility.destroyProcessCancelled(3);
                return null;
            }
            switch (option) {
                case -1:
                    FacadeUtility.destroyProcessCancelled(3);
                    return null;
                case 0:
                    FacadeUtility.destroyProcessSuccessfully(3);
                    return studentCourses;
                case 1:
                    if (courseStudentDidNotEnroll.isEmpty()) {
                        FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_SUITABLE_COURSES_FOR_STUDENT);
                    } else {
                        result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, courseStudentDidNotEnroll);
                        if (FacadeUtility.isCancelledProcess(interlayout)) {
                            return null;
                        }
                        result--;
                        if (result == -1) {
                            System.out.println("Adding course to Student is Cancelled");
                        } else {
                            studentCourses.add(courseStudentDidNotEnroll.get(result));
                            courseStudentDidNotEnroll.remove(result);
                        }
                    }
                    break;
                case 2:
                    if (studentCourses.isEmpty()) {
                        System.out.println("Student has not any course to remove it.");
                    } else {
                        result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, studentCourses);
                        if (FacadeUtility.isCancelledProcess(interlayout)) {
                            return null;
                        }
                        result--;
                        if (result == -1) {
                            System.out.println("Remove course From Student is Cancelled");
                        } else {
                            courseStudentDidNotEnroll.add(studentCourses.get(result));
                            studentCourses.remove(result);
                        }
                    }
                    break;
                case 3:
                    Course course = courseFacade.save();
                    studentCourses.add(course);
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }
        return studentCourses;
    }

    public void delete() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return;
        }
        List<Student> students = studentService.findAll();
//        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(interlayout, MetaData.PROCESS_PREFIX_STUDENT, students);
        int result = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, students);
        if (FacadeUtility.isCancelledProcess(interlayout) || result == 0) {
            FacadeUtility.destroyProcessCancelled();
            return;
        }
        result--;
        Student studentToDelete = students.get(result);
        studentService.deleteById(studentToDelete.getId());
        FacadeUtility.destroyProcessSuccessfully();
        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student(id=" + studentToDelete.getId() + ") is deleted.");
    }

    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
    }
}
