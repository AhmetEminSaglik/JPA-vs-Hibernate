package org.aes.compare.orm.consoleapplication;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;

public class ExamResultFacade {
    private final ExamResultService examResultService;
    //    private final StudentService studentService;
    private final CourseService courseService;
    private final CourseFacade courseFacade;
    private final StudentService studentService;
    private final StudentFacade studentFacade;

    /*public ExamResultFacade(ExamResultService examResultService, StudentService studentService, CourseService courseService, StudentFacade studentFacade) {
        this.examResultService = examResultService;
//        this.studentService = studentService;
        this.courseService = courseService;
        this.studentFacade = studentFacade;
    }*/

    public ExamResultFacade(ExamResultService examResultService, CourseService courseService, CourseFacade courseFacade, StudentService studentService, StudentFacade studentFacade) {
        this.examResultService = examResultService;
        this.courseService = courseService;
        this.courseFacade = courseFacade;
        this.studentService = studentService;
        this.studentFacade = studentFacade;
    }

    public ExamResult save() {
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()) {
            return null;
        }
        ExamResult examResult = new ExamResult();
        final String cancelMsg = ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXAM_RESULT_SAVE_PROCESS_IS_CANCELLED);

        Student student = studentFacade.pickStudentFromList(studentService.findAll());
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
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Error occurred: " + e.getMessage()));
            return null;
        }
        System.out.println("Exam Result is saving...");
        System.out.println("Exam Result is saved: " + examResult);

        return examResult;
    }


    public boolean isAnyExamResultSaved() {
        int totalExamResult = examResultService.findAll().size();
        if (totalExamResult == 0) {
            System.out.println(MetaData.NOT_FOUND_ANY_SAVED_EXAM_RESULT);
            return false;
        }
        return true;
    }

    private Course pickCourseThatMatchesWithStudentFromList(int studentId) {
        System.out.println("All courses that Student's enrolled: ");
        List<Course> courses = courseService.findAllCourseOfStudentId(studentId);
        return pickCourseFromList(courses);
    }

    public void findAll() {
        if (!isAnyExamResultSaved()) {
            return;
        }
        List<ExamResult> examResults = examResultService.findAll();
        printArrWithNo(examResults);
    }


    public List<ExamResult> findAllByStudentId() {
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        Student student = studentFacade.pickStudentFromList(studentService.findAll());
        if (student == null) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Find Exam results by Student process is cancelled"));
            return null;
        }
        List<ExamResult> examResults = examResultService.findAllByStudentId(student.getId());
        printArrWithNo(examResults);
        return examResults;
    }

    public List<ExamResult> findAllByStudentIdAndCourseName() {
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Not found Student. Find All Exam Result by student and course process is cancelled"));
            return null;
        }
        List<ExamResult> examResults = decidePickCourseBySelectOrTypeCourseNameOfStudent(student);

        printArrWithNo(examResults);
        return examResults;
    }

    /*private List<ExamResult> decidePickCourseBySelectOrTypeCourseName() {
        List<Course> courses = courseService.findAll();
        Course course = pickCourseFromList(courses);
        if (course != null)
            return examResultService.findAllByCourseName(course.getName());
        return null;
    }*/

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseNameOfStudent(Student student) {
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        Course course = pickCourseFromList(courses);

        if (course != null) {
            List<ExamResult> examResults = examResultService.findAllByCourseName(course.getName());
            if (examResults.isEmpty()) {
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(student.getName() + " does not have any exam result of " + course.getName() + " Course."));
            }
            return examResults;
        }
        return null;
    }

    public List<ExamResult> findAllByCourseName() {
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        List<String> indexes = new ArrayList<>();
        indexes.add("Search  with registered Courses");
        indexes.add("Search with Typing course name Manuel");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);

        List<ExamResult> examResults = null;
        switch (option) {
            case 0:
                System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                break;
            case 1:
                Course course = pickCourseFromList(courseService.findAll());
                if (course != null) {
                    printCourseDataOfExamResult(course.getName());
                }
                return findAllByCourseName();
            case 2:
                System.out.print("Type Course Name:  ");
                String courseName = SafeScannerInput.getStringNotBlank();
                printCourseDataOfExamResult(courseName);
                break;

            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
        return examResults;

    }

    private void printCourseDataOfExamResult(String courseName) {
        List<ExamResult> examResults = examResultService.findAllByCourseName(courseName);
        if (examResults == null || examResults.isEmpty()) {
            System.out.println(MetaData.getNotFoundExamResultWithCourseName(courseName));
            findAllByCourseName();
        } else {
            printArrWithNo(examResults);
        }

    }


    public void update() {
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return;
        }
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
            String sb = "(-1) Cancel & Exit\n" +
                    "(0) Save & Exit\n" +
                    "(1) Update Student  [Current : " + examResult.getStudent() + "]\n" +
                    "(2) Update Course  [Current : " + examResult.getCourse() + "]\n" +
                    "(3) Update Score  [Current : " + examResult.getScore() + "]\n";

            System.out.println(sb);
            System.out.println("Type index No to process ");
            int choose = SafeScannerInput.getCertainIntForSwitch(-1, 3);
            List<Course> courses = null;
            Student student = examResult.getStudent();
            switch (choose) {
                case -1:
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                    return examResult;
                case 0:
                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.CHANGES_ARE_UPDATED));
                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                    examResultService.update(examResult);
                    return examResult;
                case 1:
                    student = studentFacade.pickStudentFromList(studentService.findAll());
                    System.out.println("Selected Student : " + student);
                    System.out.println("examResult.getStudent() : " + examResult.getStudent());

                    if (student == null) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Student is not selected, process is cancelled"));
                        break;
                    }
                    if (student.equals(examResult.getStudent())) {
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess("You choose the same student."));
                        break;
                    }

                    courses = courseService.findAllCourseOfStudentId(student.getId());

                    if (courses == null) {
                        System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Student is not enrolled any course."));
                        break;

                    } else {
                        if ((!courses.contains(examResult.getCourse()))) {
                            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Student - Course Match. Student does not enrolled this course. Please select one of the following course. Or progress wont be saved."));
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
        if (!courseFacade.isAnyCourseSaved(MetaData.PROCESS_PREFIX_EXAM_RESULT)
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return;
        }
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
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
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
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
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
