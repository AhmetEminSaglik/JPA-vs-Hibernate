package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

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
        System.out.println(ColorfulTextDesign.getInfoColorText(counter + "-) [STUDENT] Save : "));
        Student student = new Student();
        System.out.print("Type Student name : ");
        String name = SafeScannerInput.getStringNotBlank();
        student.setName(name);

        System.out.print("Type Student grade: ");
        int grade = SafeScannerInput.getCertainIntSafe(1, 6);
        student.setGrade(grade);

        Address address = studentDecideAddressProgress();
        if (address != null) {
            System.out.println("Using address : " + address);

            student.setAddress(address);
            studentService.save(student);
            System.out.println("Student is saved : " + student);
        }else{
            System.out.println("Student Save process is Cancelled. -> Student can not save without Address Data.");
        }

        System.out.println("----------------------------------");
        return student;

    }

    private Address studentDecideAddressProgress() {
        List<Address> unmatchedAddress = findSavedAndUnMatchAddress();

        StringBuilder msg = new StringBuilder();
        msg.append("1-) Save new Address\n")
                .append("2-) Select from unmatched address (" + unmatchedAddress.size() + ")\n")
                .append("3-) Exit");

        int selected = SafeScannerInput.getCertainIntForSwitch(msg.toString(), 1, 3);

        Address address = null;

        switch (selected) {
            case 1:
                address = addressFacade.save();
                break;
            case 2:
                if (unmatchedAddress.size() > 0) {
                    StringBuilder addressSelectMsg = new StringBuilder("Select one of the following address to match the student\n");
                    addressSelectMsg.append(createMsgFromList(unmatchedAddress));

                    /* for (int i = 0; i < unmatchedAddress.size(); i++) {
                        addressSelectMsg += (i + 1) + "-) " + unmatchedAddress.get(i) + "\n";
                    }*/
                    int result = SafeScannerInput.getCertainIntForSwitch(addressSelectMsg.toString(), 1, unmatchedAddress.size() + 1);
                    result -= 1;
                    if (result == unmatchedAddress.size()) {
                        System.out.println("Address Selection is Canceled");
//                        return studentDecideAddressProgress();
                    } else {
                        address = unmatchedAddress.get(result);
                    }
                } else {
                    System.out.println(ColorfulTextDesign.getErrorColorText("There is not any unmatched addres. You must save Address first"));
//                    return studentDecideAddressProgress();
                }
                break;
            case 3:
                System.out.println("Address process for Student is Canceled ");
                return null;
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

    public Student findById() {
        System.out.print("Type number for Student id  : ");
        int studentId = SafeScannerInput.getCertainIntSafe();
        Student student = studentService.findById(studentId);
        System.out.println("Found Student by id : " + student);
        return student;
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

    public Student findByStudentIdWithCourseName() {
        System.out.print("Type number for Student id  : ");
        int studentId = SafeScannerInput.getCertainIntSafe();

        System.out.print("Type Course Name  : ");
        String courseName = SafeScannerInput.getStringNotBlank();

        Student student = null;
        try {
            student = studentService.findByStudentIdWithCourseName(studentId, courseName);
            System.out.println("Found Student : " + student);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorText(e.getMessage()));
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
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1) + "-) " + list.get(i) + "\n");
        }
        sb.append((list.size() + 1) + "-) Exit/Cancel");
        return sb;
    }

}
