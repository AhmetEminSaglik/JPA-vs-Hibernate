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

    public void findAllByStudentId() {
        Student student = pickStudentFromList();
        List<ExamResult> examResults = examResultService.findAllByStudentId(student.getId());
        printArrWithNo(examResults);
    }

    public void findAllByStudentIdAndCourseName() {
    }

    public void findAllByCourseName() {
    }

    public void update() {
    }

    public void delete() {
    }

    private void printArrWithNo(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "-) " + list.get(i));
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
}
