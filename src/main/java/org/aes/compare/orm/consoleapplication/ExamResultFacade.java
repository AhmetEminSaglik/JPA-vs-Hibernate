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
    //    private final StudentService studentService;
    private final CourseService courseService;
    private final StudentFacade studentFacade;

    public ExamResultFacade(ExamResultService examResultService, StudentService studentService, CourseService courseService, StudentFacade studentFacade) {
        this.examResultService = examResultService;
//        this.studentService = studentService;
        this.courseService = courseService;
        this.studentFacade = studentFacade;
    }

    public ExamResult save() {
        ExamResult examResult = new ExamResult();
        String cancelMsg = "Exam Result Save process is cancelled";

        Student student = studentFacade.pickStudentFromAllStudents();
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
        double score = SafeScannerInput.getCertainDoubleSafe(1, 100);

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



    private Course pickCourseThatMatchesWithStudentFromList(int studentId) {
        System.out.println("All courses that Student's enrolled: ");
        List<Course> courses = courseService.findAllCourseOfStudentId(studentId);
        return pickCourseFromList(courses);
    }

    public void findAll() {
        List<ExamResult> examResults = examResultService.findAll();
        printArrWithNo(examResults);
    }


    public List<ExamResult> findAllByStudentId() {
        Student student = studentFacade.pickStudentFromAllStudents();
        if (student == null) {
            System.out.println("Find Exam results by Student process is cancelled");
            return null;
        }
        List<ExamResult> examResults = examResultService.findAllByStudentId(student.getId());
        printArrWithNo(examResults);
        return examResults;
    }

    public List<ExamResult> findAllByStudentIdAndCourseName() {
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Not found Student. Find All Exam Result by student and course process is cancelled"));
            return null;
        }
        List<ExamResult> examResults = decidePickCourseBySelectOrTypeCourseNameOfStudent(student);

        printArrWithNo(examResults);
        return examResults;
    }

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseName() {
        List<Course> courses = courseService.findAll();
        Course course = pickCourseFromList(courses);
        if (course != null)
            return examResultService.findAllByCourseName(course.getName());
        return null;
    }

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseNameOfStudent(Student student) {
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        Course course = pickCourseFromList(courses);

        if (course != null) {
            List<ExamResult> examResults = examResultService.findAllByCourseName(course.getName());
            if (examResults.isEmpty()) {
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(student.getName() + " does not have any exam result of " + course.getName()+" Course."));
            }
            return examResults;
        }
        return null;
    }

    public List<ExamResult> findAllByCourseName() {
        StringBuilder sb = new StringBuilder();
        sb.append("(0) Cancel & Exit\n")
                .append("(1) Search  with registered Courses\n")
                .append("(2) Search with Typing course name Manuel\n");
        System.out.println(sb);
        System.out.print("Type index No : ");
        int option = SafeScannerInput.getCertainIntForSwitch("", 0, 2);
        List<ExamResult> examResults = null;
        switch (option) {
            case 0:
                System.out.println("Exiting the process.");
                break;
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

            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
        return examResults;

    }

    public void update() {
        while (true) {
            List<ExamResult> examResults = examResultService.findAll();
            ExamResult examResult = pickExamResultFromList(examResults);
            if (examResult == null) {
                break;
            } else {
                updateProcess(examResult);
            }
        }
    }

    private ExamResult updateProcess(ExamResult examResult) {
        while (true) {
            StringBuilder sb = new StringBuilder();
            sb.append("(-1) Cancel & Exit\n")
                    .append("(0) Save & Exit\n")
                    .append("(1) Update Student  [Current : " + examResult.getStudent() + "]\n")
                    .append("(2) Update Course  [Current : " + examResult.getCourse() + "]\n")
                    .append("(3) Update Score  [Current : " + examResult.getScore() + "]\n");

            System.out.println(sb);
            System.out.println("Type index No to process ");
            int choose = SafeScannerInput.getCertainIntForSwitch(-1, 3);
            List<Course> courses = null;
            Student student = examResult.getStudent();
            switch (choose) {
                case -1:
                    System.out.println("Progress is Canceled. Exiting from process");
                    return examResult;
                case 0:
                    System.out.println("Changes are updated. Exiting from process");
                    examResultService.update(examResult);
                    return examResult;
                case 1:
                    student = studentFacade.pickStudentFromAllStudents();
                    System.out.println("Selected Student : " + student);
                    System.out.println("examResult.getStudent() : " + examResult.getStudent());

                    if(student==null){
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Student is not selected, process is cancelled"));
                        break;
                    }
                    if (student.equals(examResult.getStudent())) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("You choose the same student."));
                        break;
                    }

                    courses = courseService.findAllCourseOfStudentId(student.getId());

                    if (courses == null) {
                        System.out.println(ColorfulTextDesign.getErrorColorText("Student is not enrolled any course."));
                        break;

                    } else {
                        if ((!courses.contains(examResult.getCourse()))) {
                            System.out.println(ColorfulTextDesign.getErrorColorText("Invalid Student - Course Match. Student does not enrolled this course. Please select one of the following course. Or progress wont be saved."));
                        }
                    }

                case 2:
                    if (courses == null) {
                        int studentId;
                        if (student != null) {
                            studentId = student.getId();
                        } else {
                            studentId = examResult.getStudent().getId();
                        }
                        courses = courseService.findAllCourseOfStudentId(studentId);
                    }
                    Course course = pickGenericObjectFromList(courses);
                    if (course == null) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Course is not selected. Process is cancelled"));
                    } else {
                        examResult.setStudent(student);
                        examResult.setCourse(course);
//                        try {
                        examResultService.update(examResult);
                        System.out.println("Exam Result is updated : " + examResult);
//                        } catch (InvalidStudentCourseMatchForExamResult e) {
//                            System.out.println("Error Occured : "+e.getMessage());
//                        }
                    }

                    break;
                case 3:
                    System.out.println("Type new Score (double)");
                    examResult.setScore(SafeScannerInput.getCertainDoubleSafe(1, 100));
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

    }

    public void delete() {
        while (true) {
            List<ExamResult> examResults = examResultService.findAll();
            ExamResult examResult = pickExamResultFromList(examResults);
            if (examResult == null) {
                break;
            } else {
                examResultService.deleteById(examResult.getId());
                System.out.println("Exam Result is deleted : " + examResult);
            }
        }
    }

    private ExamResult pickExamResultFromList(List<ExamResult> examResults) {
        ExamResult examResult = null;

        int inputIndex = 0;
        StringBuilder sb = createMsgFromList(examResults);
        System.out.print(sb);
        inputIndex = SafeScannerInput.getCertainIntForSwitch(0, examResults.size());
        inputIndex--;
        if (inputIndex == -1) {
            System.out.println("Exiting from Process.");
        } else {
            examResult = examResults.get(inputIndex);
        }
        return examResult;

    }

    /*private Student pickStudentFromList(List<Student> students) {
        StringBuilder sb = createMsgFromList(students);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, students.size());
        index--;
        if (index == -1) {
            return null;
        }
        return students.get(index);
    }*/

    private <T> T pickGenericObjectFromList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("List is empty. Process is cancelled");
            return null;
        }
        StringBuilder sb = createMsgFromList(list);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, list.size());
        index--;
        if (index == -1) {
            return null;
        }
        return list.get(index);
    }


    private Course pickCourseFromList(List<Course> courses) {
        StringBuilder sb = createMsgFromList(courses);
        System.out.print(sb);
        int index = SafeScannerInput.getCertainIntSafe(0, courses.size());
        index--;
        if (index == -1) {
            return null;
        }
        return courses.get(index);
    }

    private void printArrWithNo(List<?> list) {
        if (list != null) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + list.get(i));
        }
        } else {
            System.out.println("LIST IS EMPTY");
        }
    }

    private void printExamResultList(List<ExamResult> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + list.get(i));
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
    }

    /*private StringBuilder createMsgFromCourseList(List<Course> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("(0) Cancel & Exit\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append("(" + (i + 1) + ") " + list.get(i).getFileName() + "\n");
        }
        sb.append("Type index no to process : ");
        return sb;
    }*/
}
