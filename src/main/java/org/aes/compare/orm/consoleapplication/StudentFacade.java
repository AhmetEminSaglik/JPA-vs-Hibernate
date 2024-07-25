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
    private static int counter = 0;
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

//        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
        Address address = studentSaveProcessDecideAddressProgress();
        if (address == null) {
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
            System.out.println(ColorfulTextDesign.getErrorColorText(MetaData.PROCESS_RESULT_PREFIX + MetaData.STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED));
        } else {
            student.setAddress(address);
            studentService.save(student);
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + student);
        }
        FacadeUtility.printSlash();
        return student;

    }

    private Address updateStudentAddressProgress(Address address) {
//        List<Address> unmatchedAddress = findSavedAndUnMatchAddress();
//        int selected = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);
//
//        switch (selected) {
//            case 0:
//                System.out.println(ColorfulTextDesign.getTextForCanceledProcess("///////  Address is not selected. Student save process is Canceled."));
//                return null;
//            case 1:
//                address = addressFacade.save();
//                break;
//            case 2:
//                address = pickAddressFromList(unmatchedAddress);
//                break;
//            default:
//                System.out.println("Unknown process. Developer must work to fix this bug.");
//        }
//        return address;
//        if (address == null) {
//            return studentSaveProcessDecideAddressProgress();
//        }
//        return address;
        return addressFacade.updateSelectedAddress(address);
    }

    private Address studentSaveProcessDecideAddressProgress() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
        Address address = addressFacade.pickAddressForStudentSaveProcess();
//        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_ADDRESS);
        FacadeUtility.destroyProcessWithoutPrint();
        /*if (address == null) {
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
        } else {
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + address);
        }*/
        System.out.println("??????");

        return address;
    }

    private Address pickAddressFromList(List<Address> unmatchedAddress) {

//        StringBuilder addressSelectMsg = new StringBuilder("Select one of the following address to match the student\n");
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

    /*public Address pickAddressFromUnMatchedAddressList() {
        List<Address> unmatchedAddress = findSavedAndUnMatchAddress();
        return pickAddressFromList(unmatchedAddress);
    }*/

//    private List<Address> findSavedAndUnMatchAddress() {
//        return new ORMConfigSingleton().getAddressService().findAllSavedAndNotMatchedAnyStudentAddress();
//    }

    public Student findByMultipleWay() {
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_STARTS));
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        Student student = selectStudent();
//        if (student != null) {
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + student);
//        }

        FacadeUtility.printSlash();

        return student;
    }

    private Student selectStudent() {

        Student student;

        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Student from List");
        indexes.add("Pick Student by typing Student id");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);
//        final String studentNotSelectedErr = ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED);
        switch (option) {
            case 0:
//                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(studentNotSelectedErr));
//                LoggerProcessStack.addWithInnerPrefix(MetaData.EXITING_FROM_PROCESS);
//                LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
//                FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
//                return null;
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
//                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Student is not found with given Id(" + id + ").\nPlease try again."));
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(MetaData.PROCESS_RESULT_PREFIX + "Student is not found with given Id(" + id + "). Please try again."));
                    return selectStudent();
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return selectStudent();
        }
        if (student == null) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(studentNotSelectedErr));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
            System.out.println(ColorfulTextDesign.getWarningColorText(MetaData.PROCESS_RESULT_PREFIX+MetaData.STUDENT_NOT_SELECTED));
        } else {
            LoggerProcessStack.add(MetaData.PROCESS_SELECT);
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 3);
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX)+student);
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_SELECT + MetaData.PROCESS_COMPLETED));
//            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + student);
        }

        return student;


        /*
        System.out.print("Type number for Student id  : ");
        int studentId = SafeScannerInput.getCertainIntSafe();
        Student student = studentService.findById(studentId);
        System.out.println("Found Student by id : " + student);
        return student;
    */

    }

    public List<Course> findStudentAllCourses() {
//        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        Student student = findByMultipleWay();
//        List<Course> courses=courseFacade.findAllCoursesBelongsToStudent();
        if (student == null) {
            return null;
        }
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        List<Course> courses = courseFacade.findAllCoursesOfStudent(MetaData.PROCESS_PREFIX_STUDENT, student);
        FacadeUtility.destroyProcessWithoutPrint();
        if (!courses.isEmpty()) {
            printArrResult(courses);
        }
        return courses;
    }

    public Student pickStudentFromList(List<Student> students) {
        StringBuilder sb = createMsgFromList(students);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, students.size());
        index--;
        if (index == -1) {
            return null;
        }
        return students.get(index);
    }

    /*public Student pickStudentFromAllStudents() {
        if(!isAnyStudentSaved()){
            return null;
        }
        List<Student> students = studentService.findAll();
        StringBuilder sb = createMsgFromList(students);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, students.size());
        index--;
        if (index == -1) {
            return null;
        }
        return students.get(index);
    }*/


    public List<Student> findAll() {
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_STARTS));
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyStudentSaved()) {
            return null;
        }
        List<Student> students = studentService.findAll();
//        System.out.println("All students are retrieved : ");
//        System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_COMPLETED));
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_COMPLETED);
        FacadeUtility.destroyProcess(ColorfulTextDesign::getSuccessColorText, 2);
        /*for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + "-) " + students.get(i));
        }*/
        printArrResult(students);
        FacadeUtility.printSlash();
        return students;

    }

    public int getTotalStudentNumber() {
        return studentService.findAll().size();
    }

    /*public boolean isAvailableProcessToFindStudentWithStudentIdAndCourseName() {
        boolean resultStudent = isAnyStudentSaved();
        boolean resultCourse = courseFacade.isAnyCourseSaved();
        if (resultStudent && resultCourse) {
            return true;
        }
        return false;
    }*/

    public boolean isAnyStudentSaved() {
        int totalStudent = getTotalStudentNumber();
        if (totalStudent == 0) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED));
            LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_IS_CANCELLED);
            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess, 2);
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX) + ColorfulTextDesign.getWarningColorText(MetaData.NOT_FOUND_ANY_SAVED_STUDENT));
            return false;
        }
        return true;
    }


    public Student findByStudentIdWithCourseName() {
//        System.out.println(ColorfulTextDesign.getInfoColorText(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_STARTS));

//        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
//        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);

        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_STUDENT)) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED));
            return null;
        }
        if (!isAnyStudentSaved()) {
//            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_PREFIX_STUDENT + MetaData.PROCESS_READ + MetaData.PROCESS_IS_CANCELLED));
            return null;
        }


//        System.out.print("Type number for Student id  : ");
//        int studentId = SafeScannerInput.getCertainIntSafe();

        Student student = findByMultipleWay();
        if (student == null) {
//            LoggerProcessStack.add(MetaData.PROCESS_IS_CANCELLED);
//            FacadeUtility.destroyProcess(ColorfulTextDesign::getTextForCanceledProcess,2);
//            FacadeUtility.destroyProcessWithoutPrint();
//            FacadeUtility.destroyProcessWithoutPrint();
            return null;
        }
        LoggerProcessStack.add(MetaData.PROCESS_READ);
//        System.out.print("Type Course Name  : ");
//        String courseName = SafeScannerInput.getStringNotBlank();
        Course course = courseFacade.findByMultipleWay();
        if (course == null) {
            return null;
        }
        try {
            student = studentService.findByStudentIdWithCourseName(student.getId(), course.getName());
            System.out.println("Found Student : " + student);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(e.getMessage()));
        }
        return student;

    }


    public Student update() {
        if (!isAnyStudentSaved()) {
            return null;
        }
        List<Student> students = studentService.findAll();

        int selectedStudent = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_GLOBAL, students);
        selectedStudent--;
//        StringBuilder msg = new StringBuilder("Select one student's index by given list:\n");
//        msg.append(createMsgFromList(students));
//
//        int selectedStudent = SafeScannerInput.getCertainIntForSwitch(msg.toString(), 1, students.size() + 1);
//        selectedStudent--;

        if (selectedStudent == -1) {
            System.out.println("Update Process is Cancelled.");
            return null;
        }
        Student student = students.get(selectedStudent);

        List<String> indexes = new ArrayList<>();
//        StringBuilder sbStudentProcess = new StringBuilder();
        indexes.add("Update Student Name");
        indexes.add("Update Student Grade");
        indexes.add("Update Student Courses");
        indexes.add("Update Student Address");


        int option = -1;
        while (option != 5) {
            System.out.println("Student : " + student);
//            option = SafeScannerInput.getCertainIntForSwitch(sbStudentProcess.toString(), 1, 5);
            option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(MetaData.PROCESS_PREFIX_STUDENT, indexes);
            switch (option) {
                case -1:
//                    studentService.update(student);
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.STUDENT_UPDATE_PROCESS_IS_CANCELLED));
                    System.out.println("Exiting the student update process...");
                    return null;
                case 0:
                    studentService.update(student);
                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.STUDENT_IS_UPDATED + student));
                    System.out.println("Exiting the student update process...");
                    return student;

                case 1:
                    System.out.print("Type Student new Name:");
                    String name = SafeScannerInput.getStringNotBlank();
                    student.setName(name);
                    break;
                case 2:
                    System.out.print("Type Student Grade (int):");
                    int grade = SafeScannerInput.getCertainIntSafe(1, 6);
                    student.setGrade(grade);
                    break;
                case 3:
                    if (courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_STUDENT)) {
                        List<Course> courses = updateStudentCourseProgress(student.getId());
                        if (courses != null) {
                            student.setCourses(courses);
                            studentService.update(student);
                            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.STUDENT_COURSES_ARE_UPDATED));
                        }
                    }
                    break;
                case 4:
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
        List<Course> studentCourses = courseService.findAllCourseOfStudentId(studentId);
        List<Course> courseStudentDidNotEnroll = courseService.findAllCourseThatStudentDoesNotHave(studentId);

        /*if (courseFacade.isAnyCourseSaved()) {
            System.out.println(ColorfulTextDesign.getWarningColorText("Has not been found any registered Course. Please save at least one course."));
            Course course = courseFacade.save();
        }*/
        int option = -1;
        final String processPrefix = MetaData.PROCESS_PREFIX_STUDENT;
        while (option != 4) {

            System.out.println("Students' current courses : (Must finish all to save the course changes)");
            printArrWithNo(studentCourses);
            List<String> indexes = new ArrayList<>();
            indexes.add("Match course from registered Courses (" + courseStudentDidNotEnroll.size() + ")");
            indexes.add("Remove course from student Courses (" + studentCourses.size() + ")");
            indexes.add("Create new Course");

            option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndSaveExits(processPrefix, indexes);
            int result;
            switch (option) {
                case -1:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
                    return null;
                case 0:
                    return studentCourses;
//                    courseService.updateCourseByName();
                case 1:
                    if (courseStudentDidNotEnroll.isEmpty()) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.NOT_FOUND_SUITABLE_COURSES_FOR_STUDENT));
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
        if (!isAnyStudentSaved()) {
            return;
        }
//        while (option != 2) {
        List<Student> students = studentService.findAll();
//            StringBuilder sb = new StringBuilder("Please choose one of the following Student No");
//            sb.append("1-) Delete Student \n");
//            sb.append("2-) Exit\n");
//        StringBuilder sb = new StringBuilder();
//            sb.append(createMsgFromList(students));
//            option = SafeScannerInput.getCertainIntForSwitch(sb.toString(), 1, students.size());
//            switch (option) {
//                case 1:
//        StringBuilder sbMsg = new StringBuilder("Select Student no to delete.\n");
//                    sbMsg.append(createMsgFromList(students));
//        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 0, students.size());
        int result = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, students);
        result--;
        if (result == -1) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.STUDENT_DELETE_PROCESS_CANCELLED));
        } else {
            Student studentToDelete = students.get(result);
            studentService.deleteById(studentToDelete.getId());
        }
//                    break;
//                case 2:
//                    System.out.println("Exiting from delete student process...");
//                    break;
//                default:
//                    System.out.println("Unknown process. Developer must work to fix this bug.");
//            }
//        }
    }


    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
    }

    private StringBuilder createMsgFromList(List<?> list) {

        StringBuilder sb = new StringBuilder();

        sb.append("(0) Cancel & Exit\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append("(" + (i + 1) + ") " + list.get(i) + "\n");
        }
        sb.append("Type index no to process : ");
        return sb;

        /*
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1) + "-) " + list.get(i) + "\n");
        }
        sb.append((list.size() + 1) + "-) Exit/Cancel");
        return sb;
    */
    }

    private void printArrResult(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.PROCESS_RESULT_PREFIX) + (i + 1) + "-) " + list.get(i));
        }
    }

}
