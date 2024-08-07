package org.aes.compare.metadata;

import org.aes.compare.orm.utility.ColorfulTextDesign;

public class MetaData {
    public static final String SELECT_ONE_OPTION = "Please type number : ";
    public static final String PROCESS_COMPLETED = "Process is completed.";
    public static final String PROCESS_STARTS = "Process is started.";
    public static final String PROCESS_FAILED = "Process is failed.";
    public static final String PROCESS_IS_CANCELLED = "Process is cancelled. ";
    public static final String PROCESS_IS_CANCELLED_FATAL = "Process is cancelled. ";
    public static final String SWITCH_DEFAULT_INVALID_CHOICE = "Invalid Choose try again!";
    public static final String EXITING_FROM_PROCESS = "Exiting from process...";
    public static final String PROCESS_RESULT_PREFIX = " --> ";


    public static final String INNER_PROCESS_PREFIX = " >> ";
    public static final String PROCESS_PREFIX_ADDRESS = "[Address]".toUpperCase();
    public static final String PREFIX_ORM_SELECT = "[ORM-SELECT]".toUpperCase();
    public static final String PREFIX_ORM_LOGS = "[ORM-LOGS]".toUpperCase();
    public static final String PREFIX_MUSIC = "[MUSIC]".toUpperCase();
    public static final String PREFIX_PRINTING = "[PRINTING]".toUpperCase();
    public static final String PREFIX_TERMINAL_SETTING= "[TERMINAL-PREFIX]".toUpperCase();
    public static final String PREFIX_INNER_TERMINAL_PROCESS= "[TERMINAL-PROCESS]".toUpperCase();

    public static final String NOTE_PREFIX = "[NOTE] : ";
    public static final String PROCESS_PREFIX_MAIN = "[Main]".toUpperCase();
    public static final String PROCESS_PREFIX_STUDENT = "[Student]".toUpperCase();
    public static final String PROCESS_PREFIX_COURSE = "[Course]".toUpperCase();
    public static final String PROCESS_PREFIX_EXAM_RESULT = "[Exam Result]".toUpperCase();
    public static final String PROCESS_PREFIX_GLOBAL = "[Global]".toUpperCase();
    public static final String PROCESS_PREFIX_SETTINGS = "[Settings]".toUpperCase();
    public static final String AVAILABLE_OPTIONS = " Available Options:\n";

    public static final String NOT_FOUND_ANY_SAVED_ADDRESS = PROCESS_RESULT_PREFIX + "Has not found any saved address. Please save address first.";

    public static final String STUDENT_NOT_SELECTED = "Student is not selected. ";
    public static final String STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED = "Student can not save without address data.";
    public static final String NOT_FOUND_ANY_SAVED_STUDENT = "Has not found any saved student. Please save student first.";

    public static final String NOT_FOUND_SUITABLE_COURSES_FOR_STUDENT = "Not Found any suitable course for the student.";
    public static final String STUDENT_COURSES_ARE_UPDATED = "Student's courses are updated.";

    public static final String COURSE_NOT_SELECTED = "Course is not selected. ";
    public static final String NOT_FOUND_ANY_SAVED_COURSE = "Has not found any saved Courses. Please save course first.";
    public static final String COURSE_NAME_MUST_BE_UNIQUE = "Course name must be unique. ";
    public static final String IS_SAVED_ALREADY = " is saved already.";

    public static final String NOT_FOUND_ANY_SAVED_EXAM_RESULT = "Has not found any saved Exam Result. Please save Exam Result first.";
    public static final String PROCESS_SAVE = "::[SAVE]";
    public static final String PROCESS_UPDATE = "::[UPDATE]";
    public static final String PROCESS_DELETE = "::[DELETE]";
    public static final String PROCESS_READ = "::[READ]";
    public static final String PROCESS_ITEM = "::[ITEM]";

    public static String getCourseNameIsNotFound(String courseName) {
        return ColorfulTextDesign.getErrorColorText(PROCESS_RESULT_PREFIX + "Course(name=" + courseName + ") is not found.");
    }

    public static String getNotFoundExamResultWithCourseName(String courseName) {
        return ColorfulTextDesign.getErrorColorText("Not found any exam result data with course name (" + courseName + ").");
    }
}
