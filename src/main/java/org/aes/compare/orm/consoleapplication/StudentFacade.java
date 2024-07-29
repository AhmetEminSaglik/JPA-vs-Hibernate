package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.AddressService;
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
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentFacade {
    private final StudentService studentService;
    private final AddressFacade addressFacade;
    private final CourseFacade courseFacade;
    private final AddressService addressService;
    private final CourseService courseService;
    private final Scanner scanner = new Scanner(System.in);

    public StudentFacade(StudentService studentService, AddressFacade addressFacade, CourseFacade courseFacade) {
        ORMConfigSingleton orm = new ORMConfigSingleton();
        this.studentService = studentService;
        this.addressFacade = addressFacade;
        this.courseFacade = courseFacade;
        this.addressService = orm.getAddressService();
        this.courseService = orm.getCourseService();
    }

    public Student save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);

        Student student = new Student();
        System.out.print("Type Student name: ");
        String name = SafeScannerInput.getStringNotBlank();
        student.setName(name);

        System.out.print("Type Student grade: ");
        int grade = SafeScannerInput.getCertainIntSafe(1, 6);
        student.setGrade(grade);

        Address address = studentSaveProcessDecideAddressProgress();
        if (address == null) {
            FacadeUtility.destroyProcessCancelled();
            System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + MetaData.STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED));
        } else {
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

    private Address pickAddressFromList(List<Address> unmatchedAddress) {

        if (unmatchedAddress.isEmpty()) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("There is not any unmatched address. You must save Address first"));
            return null;
        }
        int result = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, unmatchedAddress);
        result--;
        if (result == -1) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Selection is Canceled"));
        } else {
            return unmatchedAddress.get(result);
        }
        return null;
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
        Student student;
        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Student from List");
        indexes.add("Pick Student by typing Student id");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);
        switch (option) {
            case 0:
                student=null;
                break;
            case 1:
                student = pickStudentFromList(studentService.findAll());
                break;
            case 2:
                System.out.print("Type Student id (int): ");
                int id = SafeScannerInput.getCertainIntSafe();
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
            FacadeUtility.destroyProcessCancelled();
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX+MetaData.STUDENT_NOT_SELECTED));
        } else {
//            LoggerProcessStack.add(MetaData.PROCESS_SELECT);
//            FacadeUtility.destroyProcessSuccessfully(3);
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX)+student);
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
        int index = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, students);
        index--;
        if (index == -1) {
            return null;
        }
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
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_STUDENT)) {
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
        if (course == null) {
            return null;
        }
        try {
            student = studentService.findByStudentIdWithCourseName(student.getId(), course.getName());
            FacadeUtility.printSuccessResult("Found Student : " + student);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(e.getMessage()));
        }
        return student;
    }


    public Student update() {
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        List<Student> students = studentService.findAll();

        int selectedStudent = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_GLOBAL, students);
        selectedStudent--;
        if (selectedStudent == -1) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        Student student = students.get(selectedStudent);
        student = updateSelectedStudent(student);
        return student;
    }

    private Student updateSelectedStudent(Student student) {
        List<String> indexes = new ArrayList<>();
        indexes.add("Update Student Name");
        indexes.add("Update Student Grade");
        indexes.add("Update Student Courses");
        indexes.add("Update Student Address");

        int option = -1;
        while (option != 5) {
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student : " + student);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX) +
                    ColorfulTextDesign.getWarningColorText(MetaData.NOTE_PREFIX) +
                    "To complete current student's update process, you should select " +
                    ColorfulTextDesign.getWarningColorText("[Save & Exit]")
                    +
                    " option.");
            option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_STUDENT, indexes);
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
                    System.out.print("Type Student new Name: ");
                    String name = SafeScannerInput.getStringNotBlank();
                    student.setName(name);
                    break;
                case 2:
                    System.out.print("Type Student Grade (int): ");
                    int grade = SafeScannerInput.getCertainIntSafe(1, 6);
                    student.setGrade(grade);
                    break;
                case 3:
                    if (courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_STUDENT)) {
                        List<Course> courses = updateStudentCourseProgress(student.getId());
                        if (courses != null) {
                            student.setCourses(courses);
                            studentService.update(student);
                            FacadeUtility.printSuccessResult(MetaData.STUDENT_COURSES_ARE_UPDATED);
                        }
                    }else{
                        LoggerProcessStack.add(MetaData.PROCESS_UPDATE);
                    }
                    break;
                case 4:
                    LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
                    Address dummyAddress = new Address(student.getAddress());
                    Address address = updateStudentAddressProgress(dummyAddress);
                    if (address != null) {
                        student.setAddress(address);
                    }
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

        return student;
    }
    private List<Course> updateStudentCourseProgress(int studentId) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);

        List<Course> studentCourses = courseService.findAllCourseOfStudentId(studentId);
        List<Course> courseStudentDidNotEnroll = courseService.findAllCourseThatStudentDoesNotHave(studentId);

        int option = -1;
        final String processPrefix = MetaData.PROCESS_PREFIX_STUDENT;
        while (option != 4) {
            List<String> indexes = new ArrayList<>();
            indexes.add("Match course from registered Courses (" + courseStudentDidNotEnroll.size() + ")");
            indexes.add("Remove course from student Courses (" + studentCourses.size() + ")");
            indexes.add("Create new Course");

            printArrWithNo(studentCourses);
            option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(processPrefix, indexes);
            int result;
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
                        result = FacadeUtility.getIndexValueOfMsgListIncludesExit(processPrefix, courseStudentDidNotEnroll);
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
                        result = FacadeUtility.getIndexValueOfMsgListIncludesExit(processPrefix, studentCourses);
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
                case 4:
                    System.out.println("Exiting the course process");
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }
        return studentCourses;
    }

    public void delete() {
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return;
        }
        List<Student> students = studentService.findAll();
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, students);
        result--;
        if (result == -1) {
            FacadeUtility.destroyProcessCancelled();
        } else {
            Student studentToDelete = students.get(result);
            studentService.deleteById(studentToDelete.getId());
            FacadeUtility.destroyProcessSuccessfully();
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + "Student(id=" + studentToDelete.getId() + ") is deleted.");
        }
    }


    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
    }
}
