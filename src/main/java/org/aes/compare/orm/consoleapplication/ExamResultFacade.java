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

    private boolean isAllowedToSaveProcess() {

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        if (!courseFacade.isAnyCourseSaved()) {
            FacadeUtility.destroyProcessWithoutPrint();
            return false;
        }
        FacadeUtility.destroyProcessWithoutPrint();

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        if (!studentFacade.isAnyStudentSaved()) {
            return false;
        }
        FacadeUtility.destroyProcessWithoutPrint();
        return true;

    }

    private Student selectStudentToSave() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        Student student = studentFacade.findByMultipleWay();
        FacadeUtility.destroyProcessWithoutPrint();
        if (student == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult("Student must be selected to save Exam Result.");
            return null;
        }
        return student;

    }

    private List<Course> getSelectedStudentEnrolledCourses(Student student) {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
        List<Course> courses = courseService.findAllCourseOfStudentId(student.getId());
        if (courses.isEmpty()) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.printColorfulWarningResult("Student has not been enrolled to any course yet.");
            return null;
        }
        return courses;
    }

    public void save() {
        TerminalCommandLayout interLayout1 = new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_SAVE, MetaData.PROCESS_STARTS);

        if (!isAllowedToSaveProcess()) {
            return;
        }
        while (interLayout1.isAllowedCurrentProcess()) {

            Student student = selectStudentToSave();
            if (student == null) {
                return;
            }
            List<Course> courses = getSelectedStudentEnrolledCourses(student);
            if (courses == null) {
                return;
            }
            TerminalCommandLayout interLayout2 = new InnerTerminalProcessLayout();

            while (interLayout2.isAllowedCurrentProcess()) {
                ExamResult examResult = new ExamResult();
                examResult.setStudent(student);

                Course course = pickCourseFromList(courses);

                examResult.setCourse(course);

                if (course == null) {
                    FacadeUtility.destroyProcessCancelled();
                    FacadeUtility.destroyProcessWithoutPrint();
                    FacadeUtility.printColorfulWarningResult("Course is not selected.");
                    break;
//            FacadeUtility.destroyProcessCancelled();
//            FacadeUtility.printColorfulWarningResult("Course must be selected to save Exam Result.");
//            return;
                }
//                FacadeUtility.destroyProcessSuccessfully(1);
                FacadeUtility.destroyProcessSuccessfully(1);
        FacadeUtility.printSuccessResult("Selected Course : " + course);

        String title = "Type Score (double): ";
//        double score = SafeScannerInput.getCertainDoubleSafe(interlayout, 1, 100);
                double score = FacadeUtility.getSafeDoubleInputFromTerminalProcess(interLayout2, title, 1, 100);
                if (FacadeUtility.isCancelledProcess(interLayout2)) {
            FacadeUtility.destroyProcessCancelled(3);
                    break;
//                    return;
        }

        examResult.setStudent(student);
        examResult.setCourse(course);
        examResult.setScore(score);

        try {
            examResultService.save(examResult);
//            FacadeUtility.destroyProcessSuccessfully(1);
//            FacadeUtility.destroyProcessWithoutPrint();
            FacadeUtility.printSuccessfullyCoreProcessLogData();
            FacadeUtility.printSuccessResult("Saved Exam Result : " + examResult);
//            FacadeUtility.destroyProcessWithoutPrint();
        } catch (InvalidStudentCourseMatchForExamResult e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("Error occurred: " + e.getMessage()));
            return;
        }
            }
        }
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

    public List<ExamResult> findAll() {
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
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
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

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
        List<ExamResult> examResults = decidePickCourseBySelectOrTypeCourseNameOfStudent(student);
        if (examResults == null) {
            FacadeUtility.destroyProcessCancelled();
            FacadeUtility.destroyProcessWithoutPrint(2);
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
//        FacadeUtility.destroyProcessWithoutPrint();
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printSuccessResult("Exam Results are retrieved : ");
        FacadeUtility.printArrResult(examResults);
        FacadeUtility.destroyProcessWithoutPrint(2);
        FacadeUtility.destroyProcessSuccessfully();

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
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_COURSE);
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()) {
            FacadeUtility.destroyProcessWithoutPrint(2);
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully(3);

        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
        if (!studentFacade.isAnyStudentSaved()) {
            FacadeUtility.destroyProcessWithoutPrint(2);
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully(4);
        if (!isAnyExamResultSaved()) {
            return null;
        }

        List<ExamResult> examResults = findAllExamResultByCourseName();
        if (examResults == null) {
            FacadeUtility.destroyProcessCancelled();
            return null;
        }
        FacadeUtility.destroyProcessSuccessfully();
        FacadeUtility.printArrResult(examResults);
        FacadeUtility.printSlash();
        return examResults;
    }

    private List<ExamResult> findAllExamResultByCourseName() {
        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);

        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        List<String> indexes = new ArrayList<>();
        indexes.add("Search with registered Courses");
        indexes.add("Search with Typing course name Manuel");
        List<ExamResult> examResults = null;
        while (interlayout.isAllowedCurrentProcess()) {
            int option = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, indexes);
            if (FacadeUtility.isCancelledProcess(interlayout)) {
//                FacadeUtility.destroyProcessCancelled();
                return null;
            }

            switch (option) {
                case 0:
//                FacadeUtility.destroyProcessCancelled();
                    return null;
                case 1:
                    Course course = pickCourseFromList(courseService.findAll());
                    if (course != null) {
                        examResults = examResultService.findAllByCourseName(course.getName());
                        if (examResults == null || examResults.isEmpty()) {
                            FacadeUtility.printErrorResult(MetaData.getNotFoundExamResultWithCourseName(course.getName()));
                        }
                    } else {
//                    FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    break;
                case 2:
                    String title = "Type Course Name: ";
                    String courseName = FacadeUtility.getSafeStringInputFromTerminalProcess(interlayout, title);
                    if (FacadeUtility.isCancelledProcess(interlayout)) {
//                    FacadeUtility.destroyProcessCancelled();
                        return null;
                    }
                    examResults = examResultService.findAllByCourseName(courseName);
                    if (examResults == null || examResults.isEmpty()) {
                        FacadeUtility.printErrorResult(MetaData.getNotFoundExamResultWithCourseName(courseName));
                    }
                    break;
                default:
                    System.out.println("Unknown process. Developer must work to fix this bug.");
            }
            if (examResults != null) {
                return examResults;
            }
        }
        return null;
    }

    public void update() {
        TerminalCommandLayout interLayout= new InnerTerminalProcessLayout();
        FacadeUtility.initProcess(MetaData.PROCESS_UPDATE, MetaData.PROCESS_STARTS);
        if (!courseFacade.isAnyCourseSaved()
                || !studentFacade.isAnyStudentSaved()
                || !isAnyExamResultSaved()) {
            return;
        }
        while (interLayout.isAllowedCurrentProcess()) {
            List<ExamResult> examResults = examResultService.findAll();
            FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
            ExamResult examResult = pickExamResultFromList(examResults);
            if (examResult == null) {
                break;
            } else {
                FacadeUtility.destroyProcessSuccessfully();
                FacadeUtility.printSuccessResult("Selected Exam Result : " + examResult);
                double newScore = updateExamResultScore(examResult.getScore());
                if (newScore == examResult.getScore()) {
                    FacadeUtility.destroyProcessCancelled();
                } else {
                    examResult.setScore(newScore);
                    examResultService.update(examResult);
                    FacadeUtility.printSuccessfullyCoreProcessLogData();
                    FacadeUtility.printSuccessResult("Updated Data : " + examResult);
                }

            }
        }
    }

    private double updateExamResultScore(double score) {
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
        String title = "Type new Score (double) ((-1) cancel): ";
//        double result = SafeScannerInput.getCertainDoubleSafe(interlayout, -1, 100);
        double result = FacadeUtility.getSafeDoubleInputFromTerminalProcess(interlayout, title, -1, 100);
        if (result == -1 || FacadeUtility.isCancelledProcess(interlayout)) {
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
            if (examResults.isEmpty()) {
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
        TerminalCommandLayout interlayout = new InnerTerminalProcessLayout();
//        FacadeUtility.initProcess(MetaData.PROCESS_SELECT, MetaData.PROCESS_STARTS);
        ExamResult examResult;

        int inputIndex = FacadeUtility.getSafeIndexValueOfMsgListIncludeExistFromTerminalProcess(interlayout, examResults);
        if (FacadeUtility.isCancelledProcess(interlayout) || inputIndex == 0) {
//            FacadeUtility.destroyProcessExiting();
//            FacadeUtility.destroyProcessExiting();
            return null;
        }
        inputIndex--;

        examResult = examResults.get(inputIndex);
//        FacadeUtility.destroyProcessSuccessfully();
        return examResult;
    }

    private Course pickCourseFromList(List<Course> courses) {
        return courseFacade.pickCourseFromList(courses);
    }

}
