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

    private static int counter = 0;

    public Student save() {
        counter++;
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(counter + "-) [STUDENT] Save : "));
        Student student = new Student();
        System.out.print("Type Student name : ");
        String name = SafeScannerInput.getStringNotBlank();
        student.setName(name);

        System.out.print("Type Student grade: ");
        int grade = SafeScannerInput.getCertainIntSafe(1, 6);
        student.setGrade(grade);

        Address address = studentDecideAddressProgress();
        if (address != null) {
            System.out.println(MetaData.ADDRESS_IS_USING);

            student.setAddress(address);
            studentService.save(student);
            System.out.println(MetaData.STUDENT_IS_SAVED + student);
        }else{
            System.out.println(MetaData.STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED);
        }

        System.out.println("----------------------------------");
        return student;

    }

    private Address studentDecideAddressProgress() {
        List<Address> unmatchedAddress = findSavedAndUnMatchAddress();

        List<String> indexes = new ArrayList<>();
        indexes.add("Save new Address");
        indexes.add("Select from unmatched address (" + unmatchedAddress.size() + ")");

/*

        StringBuilder msg = FacadeUtility.createMsgFromListExit(indexes);
        msg.insert(0, MetaData.PROCESS_PREFIX_EXAM_RESULT + MetaData.PROCESS_LIST);
        System.out.println(msg);

        int option = SafeScannerInput.getCertainIntForSwitch(MetaData.SELECT_ONE_OPTION, 0, indexes.size());
*/
        int selected = FacadeUtility.getIndexValueOfMsgListIncludesExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);
        Address address = null;

        switch (selected) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address is not selected. Student save process is Canceled."));
                return null;
            case 1:
                address = addressFacade.save();
                break;
            case 2:
                if (unmatchedAddress.size() > 0) {
                    StringBuilder addressSelectMsg = new StringBuilder("Select one of the following address to match the student\n");

//                    addressSelectMsg.append(createMsgFromList(unmatchedAddress));

                    /* for (int i = 0; i < unmatchedAddress.size(); i++) {
                        addressSelectMsg += (i + 1) + "-) " + unmatchedAddress.get(i) + "\n";
                    }*/
//                    int result = SafeScannerInput.getCertainIntForSwitch(addressSelectMsg.toString(), 1, unmatchedAddress.size() + 1);
                    int result = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, unmatchedAddress);
                    result--;
                    if (result == -1) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Address Selection is Canceled"));
//                        return studentDecideAddressProgress();
                    } else {
                        address = unmatchedAddress.get(result);
                    }
                } else {
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("There is not any unmatched address. You must save Address first"));
//                    return studentDecideAddressProgress();
                }
                break;

            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
//        return address;
        if (address == null) {
            return studentDecideAddressProgress();
        }
        return address;
    }

    private List<Address> findSavedAndUnMatchAddress() {
        return new ORMConfigSingleton().getAddressService().findAllSavedAndNotMatchedAnyStudentAddress();
    }

    public Student findByMultipleWay() {


        /*StringBuilder sp = new StringBuilder();
        sp.append("(0) Cancel & Exit\n");
        sp.append("(1) Pick Student from List\n");
        sp.append("(2) Pick Student by typing Student id\n");
        System.out.println(sp);
        String msg = "Type Index No of Option: ";
        int option = SafeScannerInput.getCertainIntForSwitch(msg, 0, 2);*/

        Student student = null;

        List<String> indexes = new ArrayList<>();
        indexes.add("Pick Student from List");
        indexes.add("Pick Student by typing Student id");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_STUDENT, indexes);
        final String studentNotSelectedErr = "Student is not selected. Process is cancelled.";
        switch (option) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(studentNotSelectedErr));
                break;
            case 1:
                student = pickStudentFromList(studentService.findAll());
                if (student == null) {
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(studentNotSelectedErr));
                } else {
                    System.out.println(ColorfulTextDesign.getSuccessColorText("Selected Student : ") + student);
                }
                break;
            case 2:
                System.out.print("Type Student id (int): ");
                int id = SafeScannerInput.getCertainIntSafe();
                student = studentService.findById(id);
                if (student == null) {
                    System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Student is not found with given Id(" + id + "). Please try again"));
                    return findByMultipleWay();
                } else {
                    System.out.println(ColorfulTextDesign.getSuccessColorText("Selected Student : ") + student);
                }
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return findByMultipleWay();
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

    public Student pickStudentFromAllStudents() {
        List<Student> students = studentService.findAll();
        StringBuilder sb = createMsgFromList(students);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, students.size());
        index--;
        if (index == -1) {
            return null;
        }
        return students.get(index);
    }


    public List<Student> findAll() {
        List<Student> students = studentService.findAll();
        System.out.println("All students are retrieved : ");
        /*for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + "-) " + students.get(i));
        }*/
        printArrWithNo(students);
        System.out.println("-------------------------");
        return students;

    }

    public int getTotalStudentNumber() {
        return studentService.findAll().size();
    }

    public boolean isAvailableProcessToFindStudentWithStudentIdAndCourseName() {
        boolean resultStudent = isAnyStudentSaved();
        boolean resultCourse = isAnCourseSaved();
        if (resultStudent && resultCourse) {
            return true;
        }
        return false;
    }

    public boolean isAnyStudentSaved() {
        int totalStudent = getTotalStudentNumber();
        if (totalStudent == 0) {
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_STUDENT);
            return false;
        }
        return true;
    }

    public boolean isAnCourseSaved() {
        int totalCourse = courseFacade.getTotalCourseNumber();
        if (totalCourse == 0) {
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_COURSE);
            return false;
        }
        return true;
    }
    public Student findByStudentIdWithCourseName() {
//        System.out.print("Type number for Student id  : ");
//        int studentId = SafeScannerInput.getCertainIntSafe();


        Student student = findByMultipleWay();
        if (student == null) {
            return null;
        }
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

        List<Student> students = studentService.findAll();
        StringBuilder msg = new StringBuilder("Select one student's index by given list:\n");
        msg.append(createMsgFromList(students));

        int selectedStudent = SafeScannerInput.getCertainIntForSwitch(msg.toString(), 1, students.size() + 1);
        selectedStudent--;

        if (selectedStudent == students.size()) {
            System.out.println("Update Process is Cancelled.");
            return null;
        }
        Student student = students.get(selectedStudent);
        int option = -1;

        StringBuilder sbStudentProcess = new StringBuilder();
        sbStudentProcess.append("1-) Update Student Name\n");
        sbStudentProcess.append("2-) Update Student Grade\n");
        sbStudentProcess.append("3-) Update Student Courses\n");
        sbStudentProcess.append("4-) Update Student Address\n");
        sbStudentProcess.append("5-) Save And Exit\n");
        sbStudentProcess.append("Select process No");


        while (option != 5) {
            System.out.println("Student : " + student);
            option = SafeScannerInput.getCertainIntForSwitch(sbStudentProcess.toString(), 1, 5);
            switch (option) {
                case 1:
                    System.out.print("Type Student new Name:");
                    String name = scanner.nextLine();
                    student.setName(name);
                    break;
                case 2:
                    System.out.print("Type Student new Name:");
                    int grade = scanner.nextInt();
                    scanner.nextLine();
                    student.setGrade(grade);
                    break;
                case 3:
                    List<Course> courses = studentDecideCoursesProgress(student.getId());
                    student.setCourses(courses);
                    studentService.update(student);
                    break;
                case 4:
                    Address address = studentDecideAddressProgress();
                    student.setAddress(address);
                    break;
                case 5:
                    studentService.update(student);
                    System.out.println("Exiting the student update process...");
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

        return student;
    }

    private List<Course> studentDecideCoursesProgress(int studentId) {
        List<Course> studentCourses = courseService.findAllCourseOfStudentId(studentId);
        List<Course> courseStudentDidNotEnroll = courseService.findAllCourseThatStudentDoesNotHave(studentId);
        int option = -1;
        while (option != 4) {

            System.out.println("Students' current courses : (Must finish all to save the course changes)");
            printArrWithNo(studentCourses);
            System.out.println("------");

            System.out.println("1-) Create new Course");
            System.out.println("2-) Match course from registered Courses (" + courseStudentDidNotEnroll.size() + ")");
            System.out.println("3-) Remove course from student Courses (" + studentCourses.size() + ")");
            System.out.println("4-) Save and exit ");
            option = SafeScannerInput.getCertainIntForSwitch("", 1, 4);
            StringBuilder sbMsg = new StringBuilder();
            int result = -1;
            switch (option) {
                case 1:
                    Course course = courseFacade.save();
                    studentCourses.add(course);
//                    System.out.println("CourseFacade Simdilik Kapali baska tercih ypain");
//                    Course course = courseFacade.save();
//                    studentCourses.add(course);
                    break;
                case 2:
                    if (courseStudentDidNotEnroll.size() == 0) {
                        System.out.println("Not Found any suitable course for the student");
                    } else {
                    sbMsg.append("Please choose one of the following course no\n");
                    sbMsg.append(createMsgFromList(courseStudentDidNotEnroll));
                        result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 1, courseStudentDidNotEnroll.size() + 1);
                        result--;

                        if (result == courseStudentDidNotEnroll.size()) {
                            System.out.println("Adding course to Student is Cancelled");
                        } else {
                            studentCourses.add(courseStudentDidNotEnroll.get(result));
                            courseStudentDidNotEnroll.remove(result);
                        }


                    }
                    break;
                case 3:

                    if (studentCourses.size() == 0) {
                        System.out.println("Student has not any course to remove it.");
                    } else {

                    sbMsg.append("Please choose one of the following course no\n");
                    /*for (int i = 0; i < studentCourses.size(); i++) {
                        msg += (i + 1) + "-) " + studentCourses.get(i) + "\n";
                    }*/
                    sbMsg.append(createMsgFromList(studentCourses));
//                    sbMsg.append((studentCourses.size() + 1) + "-) Exit");
//                    msg createMsgFromList (studentCourses);
                        result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 1, studentCourses.size() + 1);
                    result--;
                        if (result == studentCourses.size()) {
                            System.out.println("Remove course From Student is Cancelled");
                        } else {
                            courseStudentDidNotEnroll.add(studentCourses.get(result));
                    studentCourses.remove(result);
                        }
                    }
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
        StringBuilder sbMsg = new StringBuilder("Select Student no to delete.\n");
                    sbMsg.append(createMsgFromList(students));
        int result = SafeScannerInput.getCertainIntForSwitch(sbMsg.toString(), 1, students.size() + 1);
                    result--;
        if (result == students.size()) {
            System.out.println("Student Delete process is Cancelled");
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
    */}

}
