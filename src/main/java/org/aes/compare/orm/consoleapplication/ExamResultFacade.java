package org.aes.compare.orm.consoleapplication;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.List;

public class ExamResultFacade {
    private final ExamResultService examResultService;
    private final StudentService studentService;
    private final CourseService courseService;

    public ExamResultFacade(ExamResultService examResultService, StudentService studentService, CourseService courseService) {
        this.examResultService = examResultService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public ExamResult save() {
        ExamResult examResult = new ExamResult();
        String cancelMsg = "Exam Result Save process is canceled";

        Student student = pickStudentFromList();
        if (student == null) {
            System.out.println(cancelMsg);
            return null;
        }

        Course course = pickCourseThatMatchesWithStudentFromList(student.getId());
        if (course == null) {
            System.out.println(cancelMsg);
            return null;
        }

        System.out.print("Type Score (double): ");
        double score = SafeScannerInput.getCertainDoubleSafe(0, 100);

        examResult.setStudent(student);
        examResult.setCourse(course);
        examResult.setScore(score);

        try {
            examResultService.save(examResult);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorText("Error occurred: " + e.getMessage()));
            return null;
        }
        System.out.println("Exam Result is saving...");
        System.out.println("Exam Result is saved: " + examResult);

        return examResult;
    }

    Student decidePickStudentBySelectOrTypeId() {
        StringBuilder sp = new StringBuilder();
        sp.append("1-) Pick Student from List\n");
        sp.append("2-) Pick Student by typing Student id\n");
        sp.append("3-) Exit/Cancel\n");
        System.out.println(sp);
        String msg = "Type Index No of Option: ";
        Student student = null;
        int option = SafeScannerInput.getCertainIntForSwitch(msg, 1, 3);

        switch (option) {
            case 1:
                return pickStudentFromList();
            case 2:
                System.out.print("Type Student id (int)");
                int id = SafeScannerInput.getCertainIntSafe();
                return studentService.findById(id);
            case 3:
                System.out.println("");
                return null;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
                return decidePickStudentBySelectOrTypeId();
        }

    }

    private Student pickStudentFromList() {
        List<Student> students = studentService.findAll();
        StringBuilder sb = createMsgFromList(students);
        System.out.println(sb);
        System.out.println("Type Student's Order Value:");
        int index = SafeScannerInput.getCertainIntSafe(1, students.size() + 1);
        index--;
        if (index == students.size()) {
            return null;
        }
        return students.get(index);
    }

    private Course pickCourseThatMatchesWithStudentFromList(int studentId) {
//        List<Course> courses = courseService.findAll();
        List<Course> courses = courseService.findAllCourseOfStudentId(studentId);
        StringBuilder sb = new StringBuilder("All courses that Student's enrolled:\n");
        sb.append(createMsgFromList(courses));
        System.out.println(sb);
        System.out.println("Type Course's Order Value:");
        int index = SafeScannerInput.getCertainIntSafe(1, courses.size() + 1);
        index--;

        if (index == courses.size()) {
            return null;
        }
        return courses.get(index);
    }

    public void findAll() {
        List<ExamResult> examResults = examResultService.findAll();
        printArrWithNo(examResults);
    }

    public List<ExamResult> findAllByStudentId() {
        Student student = pickStudentFromList();
        if (student == null) {
            System.out.println("Find Exam results by Student process is cancelled");
            return null;
        }
        List<ExamResult> examResults = examResultService.findAllByStudentId(student.getId());
        printArrWithNo(examResults);
        return examResults;
    }

    public List<ExamResult> findAllByStudentIdAndCourseName() {
//        Student student = pickStudentFromList();
        Student student = decidePickStudentBySelectOrTypeId();
        if (student == null) {
            System.out.println("Not found Student. Find All Exam Result by student and course process is cancelled");
            return null;
        }
//        List<ExamResult> examResults = exaResultService.findAllByStudentId(student.getId());
        List<ExamResult> examResults = decidePickCourseBySelectOrTypeCourseNameOfStudent(student.getId());
        
        /*   System.out.print("Type course name : ");
        String courseName = SafeScannerInput.getStringNotBlank();
        List<ExamResult> examResults = examResultService.findAllByStudentIdAndCourseName(student.getId(), courseName);
        if (examResults == null) {
            System.out.println("Exam Result data is not found as requested Data");
            return null;
        }*/
        printArrWithNo(examResults);
        return examResults;
    }

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseName() {
        List<Course> courses = courseService.findAll();
        StringBuilder msg = createMsgFromCourseList(courses);
        System.out.println(msg);
        System.out.print("Type Course index no : ");
        int indexNo = SafeScannerInput.getCertainIntForSwitch("", 1, courses.size() + 1);
        indexNo--;
        return examResultService.findAllByCourseName(courses.get(indexNo).getName());
    }

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseNameOfStudent(int studentId) {
        List<Course> courses = courseService.findAllCourseOfStudentId(studentId);
        StringBuilder msg = createMsgFromCourseList(courses);
        System.out.println(msg);
        System.out.print("Type Course index no : ");
        int indexNo = SafeScannerInput.getCertainIntForSwitch("", 1, courses.size() + 1);
        indexNo--;
        if (indexNo == courses.size()) {
            System.out.println("Process is cancelled");
            return null;
        }
        return examResultService.findAllByCourseName(courses.get(indexNo).getName());
    }

    public List<ExamResult> findAllByCourseName() {
        StringBuilder sb = new StringBuilder();
        sb.append("1-) Search  with registered Courses\n")
                .append("2-) Search with Typing course name Manuel\n")
                .append("3-) Exit/Cancel");
        System.out.println(sb);
        System.out.print("Type index No : ");
        int option = SafeScannerInput.getCertainIntForSwitch("", 1, 3);
        List<ExamResult> examResults = null;
        switch (option) {
            case 1:
                examResults = decidePickCourseBySelectOrTypeCourseName();
                printArrWithNo(examResults);
                break;
            case 2:
                System.out.print("Type Course Name:  ");
                String courseName = SafeScannerInput.getStringNotBlank();
                examResults = examResultService.findAllByCourseName(courseName);
                if (examResults == null) {
                    System.out.println("Exam Result data is not found as requested Data");
                    return null;
                }
                printArrWithNo(examResults);
                break;
            case 3:
                System.out.println("Exiting the process.");
                break;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
        return examResults;

    }

    public void update() {
    }

    public void delete() {
    }

    private void printArrWithNo(List<?> list) {
        if (list != null) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
        }
        } else {
            System.out.println("LIST IS EMPTY");
        }
    }

    private void printExamResultList(List<ExamResult> list) {
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

    private StringBuilder createMsgFromCourseList(List<Course> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append((i + 1) + "-) " + list.get(i).getName() + "\n");
        }
        sb.append((list.size() + 1) + "-) Exit/Cancel");
        return sb;
    }
}
