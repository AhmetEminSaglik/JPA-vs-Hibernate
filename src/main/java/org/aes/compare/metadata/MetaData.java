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


    public static final String PROCESS_PREFIX_ADDRESS = "[Address]: ".toUpperCase();
    public static final String PROCESS_PREFIX_STUDENT = "[Student]: ".toUpperCase();
    public static final String PROCESS_PREFIX_COURSE = "[Course]: ".toUpperCase();
    public static final String PROCESS_PREFIX_EXAM_RESULT = "[Exam Result]: ".toUpperCase();
    public static final String PROCESS_PREFIX_GLOBAL = "[Global]: ".toUpperCase();
    public static final String PROCESS_PREFIX_SETTINGS = "[Settings]: ".toUpperCase();
    public static final String AVAILABLE_OPTIONS = "Available Options: \n";

    public static final String ADDRESS_IS_UPDATED = "Address is updated: ";
    public static final String ADDRESS_IS_USING = "Using address is: ";
    public static final String NOT_FOUND_ANY_SAVED_ADDRESS = PROCESS_RESULT_PREFIX + "Has not found any saved address. Please save address first.";

    public static final String STUDENT_IS_UPDATED = "Student is updated: ";
    public static final String STUDENT_IS_SAVED = "Student is saved: ";
    public static final String STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED = "Student can not save without Address Data.\n" + PROCESS_IS_CANCELLED;
    public static final String NOT_FOUND_ANY_SAVED_STUDENT = "Has not found any saved student. Please save student first.\n" + PROCESS_IS_CANCELLED;
    public static final String STUDENT_UPDATE_PROCESS_IS_CANCELLED = "Student update Process is cancelled ";

    public static final String NOT_FOUND_SUITABLE_COURSES_FOR_STUDENT = "Not Found any suitable course for the student.";
    public static final String STUDENT_COURSES_ARE_UPDATED = "Not Found any suitable course for the student.";
    public static final String STUDENT_DELETE_PROCESS_CANCELLED = "Student Delete process is Cancelled";

    public static final String COURSE_NOT_SELECTED_PROCESS_CANCELLED = "Course is not selected.\n" + PROCESS_IS_CANCELLED;
    public static final String COURSE_IS_SAVED = "Course is saved: ";
    public static final String COURSE_IS_UPDATED = "Course is updated: ";
    public static final String COURSE_SELECTED = "Selected Course: ";
    public static final String COURSE_IS_DELETED = "Course is deleted";
    public static final String NOT_FOUND_ANY_SAVED_COURSE = "Has not found any saved Courses. Please save course first.\n" + PROCESS_IS_CANCELLED;
    public static final String COURSE_NAME_MUST_BE_UNIQUE = "Course name must be unique. " + PROCESS_IS_CANCELLED_FATAL;
    public static final String IS_SAVED_BEFORE = " is saved Before.";

    public static final String NOT_FOUND_ANY_SAVED_EXAM_RESULT = "Has not found any saved Exam Result. Please save Exam Result first.\n" + PROCESS_IS_CANCELLED;
    public static final String EXAM_RESULT_SAVE_PROCESS_IS_CANCELLED = "Exam Result Save process is cancelled. ";
    public static final String CHANGES_ARE_UPDATED = "Changes are updated.";

    public static String getCourseNameIsNotFound(String courseName) {
        return ColorfulTextDesign.getErrorColorText("Course with course name (" + courseName + ") is not found.");
    }

    public static String getNotFoundExamResultWithCourseName(String courseName) {
        return ColorfulTextDesign.getErrorColorText("Not found any exam result data with course name (" + courseName + ").");
    }


    public static final String PROCESS_SAVE = "[SAVE] -> ";
    public static final String PROCESS_UPDATE = "[UPDATE] -> : ";
    public static final String PROCESS_DELETE = "[DELETE] -> : ";
    public static final String PROCESS_READ = "[READ] -> : ";
}
