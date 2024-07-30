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
import org.aes.compare.uiconsole.business.LoggerProcessStack;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

import java.util.ArrayList;
import java.util.List;

public class ExamResultFacade {
    private final ExamResultService examResultService;
    private final CourseService courseService;
    private final CourseFacade courseFacade;
    private final StudentService studentService;
    private final StudentFacade studentFacade;

    public ExamResultFacade(ExamResultService examResultService, CourseService courseService, CourseFacade courseFacade, StudentService studentService, StudentFacade studentFacade) {
        this.examResultService = examResultService;
        this.courseService = courseService;
        this.courseFacade = courseFacade;
        this.studentService = studentService;
        this.studentFacade = studentFacade;
    }

    public ExamResult save() {
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        if (!courseFacade.isAnyCourseSaved()) {
            FacadeUtility.destroyProcessWithoutPrint();
            return null;
        }
        FacadeUtility.destroyProcessWithoutPrint();

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        if (!studentFacade.isAnyStudentSaved()) {
            return null;
        }
        FacadeUtility.destroyProcessWithoutPrint();
        ExamResult examResult = new ExamResult();

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        Student student = studentFacade.findByMultipleWay();
        FacadeUtility.destroyProcessWithoutPrint();
        if (student == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult("Student must be selected to save Exam Result.");
            return null;
        }

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_READ,MetaData.PROCESS_STARTS);
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult("Student has not been enrolled to any course yet.");
            return null;
        }
//        FacadeUtility.getAllInOrderWithAvailableOptions();
//        FacadeUtility.printSuccessResult("All courses that Student's enrolled are listed below :");
        Course course = pickCourseFromList(courses);

        if (course == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.printColorfulWarningResult("Course is not selected.");

            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult("Course must be selected to save Exam Result.");
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printSuccessResult("Selected Course : "+course);

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
        FacadeUtility.destroyProcessWithoutPrint();
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printSuccessResult("Saved Exam Result : "+examResult);
        return examResult;
    }

    public boolean isAnyExamResultSaved() {
        int totalExamResult = examResultService.findAll().size();
        if (totalExamResult == 0) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult(MetaData.NOT_FOUND_ANY_SAVED_EXAM_RESULT);
            return false;
        }
        return true;
    }

    /*private Course pickCourseThatMatchesWithStudentFromList(int studentId) {
        System.out.println();
//        System.out.println(LoggerProcessStack.getAllInOrder() + MetaData.PROCESS_RESULT_PREFIX + "All courses that Student's enrolled: ");
        List<Course> courses = courseService.findAllCourseOfStudentId(studentId);
        if (courses.isEmpty()) {

            return null;
        }

        return pickCourseFromList(courses);
    }*/

    public List<ExamResult>  findAll() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!isAnyExamResultSaved()) {
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully();
        List<ExamResult> examResults = examResultService.findAll();
        FacadeUtility.printArrResult(examResults);
        return examResults;
    }


    public List<ExamResult> findAllByStudentId() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessWithoutPrint();
            return null;
        }
        FacadeUtility.destroyProcessWithoutPrint();
        List<ExamResult> examResults = examResultService.findAllByStudentId(student.getId());
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(examResults);
        return examResults;
    }

    public List<ExamResult> findAllByStudentIdAndCourseName() {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        if (!courseFacade.isAnyCourseSaved()
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        Student student = studentFacade.findByMultipleWay();
        if (student == null) {
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        List<ExamResult> examResults = decidePickCourseBySelectOrTypeCourseNameOfStudent(student);
        if (examResults == null) {
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        FacadeUtility.destroyProcessWithoutPrint();
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printSuccessResult("Exam Results are retrieved : ");
        FacadeUtility.printArrResult(examResults);
        return examResults;
    }

    private List<ExamResult> decidePickCourseBySelectOrTypeCourseNameOfStudent(Student student) {
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        // todo 3
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
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);

        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);

        if (!courseFacade.isAnyCourseSaved()
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return null;
        }
        List<ExamResult> examResults = findAllExamResultByCourseName();
        if (examResults == null) {
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.destroyProcessWithoutPrint();
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(examResults);
        FacadeUtility.printSlash();
        return examResults;
    }

    private List<ExamResult> findAllExamResultByCourseName() {
        List<String> indexes = new ArrayList<>();
        indexes.add("Search  with registered Courses");
        indexes.add("Search with Typing course name Manuel");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);

        List<ExamResult> examResults = null;
        switch (option) {
            case 0:
                return null;
            case 1:
                // todo 4
                Course course = pickCourseFromList(courseService.findAll());
                if (course != null) {
                    examResults = examResultService.findAllByCourseName(course.getName());
                    if (examResults == null || examResults.isEmpty()) {
                        FacadeUtility.printErrorResult(MetaData.getNotFoundExamResultWithCourseName(course.getName()));
                        return findAllExamResultByCourseName();
                    }
                    return examResults;
                }
                return null;
            case 2:
                System.out.print("Type Course Name:  ");
                String courseName = SafeScannerInput.getStringNotBlank();
                examResults = examResultService.findAllByCourseName(courseName);
                if (examResults == null || examResults.isEmpty()) {
                    FacadeUtility.printErrorResult(MetaData.getNotFoundExamResultWithCourseName(courseName));
                    return findAllExamResultByCourseName();
                }
                return examResults;
            default:
                System.out.println("Unknown process. Developer must work to fix this bug.");
        }
        return examResults;


    }

    public void update() {
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);

        if (!courseFacade.isAnyCourseSaved()
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
                FacadeUtility.initProcess(MetaData.PROCESS_ITEM, MetaData.PROCESS_STARTS);
                FacadeUtility.printSuccessResult("Selected Exam Result : " + examResult);
                double newScore = updateExamResultScore(examResult.getScore());
                if (newScore == examResult.getScore()) {
                    FacadeUtility.destroyProcessCancelled();
                } else {
                    examResult.setScore(newScore);
                    examResultService.update(examResult);
                    FacadeUtility.printSuccessResult("Current Exam Result : " + examResult);
                    FacadeUtility.destroyProcessSuccessfully();
                }

            }
        }
    }

    private double updateExamResultScore(double score) {
        System.out.print("Type new Score (double) (-1 to cancel): ");
        double result = SafeScannerInput.getCertainDoubleSafe(-1, 100);
        if (result == -1) {
            return score;
        } else if (result >= -1 && result < 1) {
            FacadeUtility.printColorfulWarningResult("Minimum score is allowed to 1.");
            result = 1;
        }

        return result;
    }

    /*private ExamResult updateProcess(ExamResult examResult) {
        while (true) {
            FacadeUtility.printSuccessResult("Current Exam Result : " + examResult);
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
//                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_IS_CANCELLED));
//                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                    FacadeUtility.destroyProcessCancelled(1);
                    return examResult;
                case 0:
//                    System.out.println(ColorfulTextDesign.getSuccessColorText(MetaData.CHANGES_ARE_UPDATED));
//                    System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.EXITING_FROM_PROCESS));
                    examResultService.update(examResult);
                    FacadeUtility.destroyProcessSuccessfully(1);
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
                        System.out.println(ColorfulTextDesign.getTextForCanceledProcess(MetaData.PROCESS_RESULT_PREFIX + "You choose the same student."));
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
                    System.out.print("Type new Score (double) (-1 to cancel): ");
                    double result = SafeScannerInput.getCertainDoubleSafe(-1, 100);
                    if (result == -1) {
                        FacadeUtility.printColorfulCancelResult("Typing score process is cancelled.");
                        break;
                    }
                    if (result >= -1 && result < 1) {
                        FacadeUtility.printColorfulWarningResult("Minimum score is allowed to 1.");
                        result = 1;
                    }
                    examResult.setScore(result);
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
        }

    }*/

    public void delete() {
        FacadeUtility.initProcess(MetaData.PROCESS_DELETE, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return;
        }
        while (true) {
            List<ExamResult> examResults = examResultService.findAll();
            if(examResults.isEmpty()){
                FacadeUtility.destroyProcessExiting();
                FacadeUtility.printColorfulWarningResult("Not found any more saved Exam Result data.");
                break;
            }
            ExamResult examResult = pickExamResultFromList(examResults);
            if (examResult == null) {
                break;
            } else {
                examResultService.deleteById(examResult.getId());
                FacadeUtility.destroyProcessSuccessfully(1);
                FacadeUtility.printSuccessResult("Exam Result(id=" + examResult.getId() + ") is deleted : ");
            }
        }
    }

    private ExamResult pickExamResultFromList(List<ExamResult> examResults) {
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        ExamResult examResult = null;
        FacadeUtility.getAllInOrderWithAvailableOptions();

        int inputIndex = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, examResults);
        inputIndex--;

        if (inputIndex == -1) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessExiting();
        } else {
            examResult = examResults.get(inputIndex);
            FacadeUtility.destroyProcessSuccessfully();
        }
        return examResult;

    }

    private Course pickCourseFromList(List<Course> courses) {
//        StringBuilder sb = createMsgFromList(courses);
//        System.out.print(sb);
//        courseFacade.pickCourseFromList(courses);
//        int index = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(MetaData.PROCESS_PREFIX_EXAM_RESULT, courses);
//        index--;
//        if (index == -1) {
//            return null;
//        }
        return courseFacade.pickCourseFromList(courses);
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

}
