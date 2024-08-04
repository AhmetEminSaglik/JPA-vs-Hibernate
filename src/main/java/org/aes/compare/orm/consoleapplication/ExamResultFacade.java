package org.aes.compare.orm.consoleapplication;

import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.business.concretes.InnerTerminalProcessLayout;
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

public class ExamResultFacade extends TerminalCommandLayout {
    private final ExamResultService examResultService;
    private final CourseService courseService;
    private final CourseFacade courseFacade;
    private final StudentFacade studentFacade;

    public ExamResultFacade(ExamResultService examResultService, CourseService courseService, CourseFacade courseFacade, StudentService studentService, StudentFacade studentFacade) {
        this.examResultService = examResultService;
        this.courseService = courseService;
        this.courseFacade = courseFacade;
        this.studentFacade = studentFacade;
    }

    public ExamResult save() {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
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
        double score = SafeScannerInput.getCertainDoubleSafe(interlayout, 1, 100);
        if (FacadeUtility.isEqualsToTerminalCompletedProcessValue(score)) {
            return null;
        }

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
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()) {
            FacadeUtility.destroyProcessWithoutPrint(2);
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully(3);

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        FacadeUtility.initProcess(MetaData.PROCESS_READ, MetaData.PROCESS_STARTS);
        if (!studentFacade.isAnyStudentSaved()) {
            FacadeUtility.destroyProcessWithoutPrint(2);
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully(3);

        if (!isAnyExamResultSaved()) {
            return null;
        }

        List<ExamResult> examResults = findAllExamResultByCourseName();
        if (examResults == null) {
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(examResults);
        FacadeUtility.printSlash();
        return examResults;
    }

    private List<ExamResult> findAllExamResultByCourseName() {
// examreuslt 4'de -q 'de main'e geciyor

        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        List<String> indexes = new ArrayList<>();
        indexes.add("Search with registered Courses");
        indexes.add("Search with Typing course name Manuel");
        int option = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(interlayout,MetaData.PROCESS_PREFIX_EXAM_RESULT, indexes);
        if (FacadeUtility.isOptionEqualsToCMDCancelProcessValue(option)) {
            return null;
        }
        if (FacadeUtility.isOptionEqualsToCMDLineParserValue(option)) {
            return findAllExamResultByCourseName();
        }
        List<ExamResult> examResults = null;
        switch (option) {

            case 0:
                FacadeUtility.destroyProcessCancelled();
                return null;
            case 1:
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
                String courseName = SafeScannerInput.getStringNotBlank(interlayout);
                if (FacadeUtility.isCancelledProcess(interlayout)) {
                    return null;
                }
                if (FacadeUtility.isOptionEqualsToCMDLineParserValue(courseName)) {
                    return findAllExamResultByCourseName();
                }
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
        double result = SafeScannerInput.getCertainDoubleSafe(this, -1, 100);
        if (result == -1 || FacadeUtility.isEqualsToTerminalCompletedProcessValue(result)) {
            return score;
        } else if (result >= -1 && result < 1) {
            FacadeUtility.printColorfulWarningResult("Minimum score is allowed to 1.");
            result = 1;
        }

        return result;
    }


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

        int inputIndex = FacadeUtility.getIndexValueOfMsgListIncludesCancelAndExit(this,MetaData.PROCESS_PREFIX_EXAM_RESULT, examResults);
        if (FacadeUtility.isEqualsToTerminalCompletedProcessValue(inputIndex)) {
            return null;
        }
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
        return courseFacade.pickCourseFromList(courses);
    }

}
