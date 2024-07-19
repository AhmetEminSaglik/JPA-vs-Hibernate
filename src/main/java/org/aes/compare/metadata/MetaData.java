package org.aes.compare.metadata;

import org.aes.compare.orm.utility.ColorfulTextDesign;

public class MetaData {
    public  static  final  String SELECT_ONE_OPTION="Please type option number to run that process: ";
    public  static  final  String PROCESS_IS_CANCELLED=ColorfulTextDesign.getTextForCanceledProcess("Process is canceled. ");
    public  static  final  String PROCESS_IS_CANCELLED_FATAL=ColorfulTextDesign.getErrorColorText("Process is canceled. ");
    public  static  final String SWITCH_DEFAULT_INVALID_CHOICE= ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Choose try again!");
    public static final String EXITING_FROM_PROCESS = ColorfulTextDesign.getTextForCanceledProcess("Exiting from process...");
    public  static  final String PROCESS_PREFIX_ADDRESS="[Address]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_STUDENT="[Student]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_COURSE="[Course]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_EXAM_RESULT="[Exam Result]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_GLOBAL="[Global]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_SETTINGS="[Settings]: ".toUpperCase();
    public  static  final String PROCESS_LIST="Process List\n";
    private static final StringBuilder sbForExamResuyltWarning = new StringBuilder(
            ColorfulTextDesign.getErrorColorText("Without creating any Student or Course, you may not run the functions of the Exam Result processes.\nStrongly recommend to create " +
                    ColorfulTextDesign.getInfoColorText("Student") +
                    ColorfulTextDesign.getErrorColorText(" and ") +
                    ColorfulTextDesign.getInfoColorText("Course") +
                    ColorfulTextDesign.getErrorColorText(" data before visit this option. ")));
    

    public static final String GLOBAL_WARNING_FOR_EXAM_RESULT = sbForExamResuyltWarning.toString();


    public static final String ADDRESS_IS_UPDATED = ColorfulTextDesign.getSuccessColorText("Address is updated: ");
    public static final String ADDRESS_IS_USING = ColorfulTextDesign.getSuccessColorText("Using address is: ");
    public static final String NOT_FOUND_ANY_SAVED_ADDRESS = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved address. Please save address first. "+PROCESS_IS_CANCELLED);


    public static final String STUDENT_IS_UPDATED = ColorfulTextDesign.getSuccessColorText("Student is updated: ");
    public static final String STUDENT_IS_SAVED = ColorfulTextDesign.getSuccessColorText("Student is saved: ");
    public static final String STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED= ColorfulTextDesign.getErrorColorTextWithPrefix("Student can not save without Address Data. "+PROCESS_IS_CANCELLED);
    public static final String NOT_FOUND_ANY_SAVED_STUDENT = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved student. Please save student first. "+PROCESS_IS_CANCELLED);
    public static final String STUDENT_UPDATE_PROCESS_IS_CANCELLED= ColorfulTextDesign.getTextForCanceledProcess("Student update Process is canceled ");
    public static final String NOT_FOUND_SUITABLE_COURSES_FOR_STUDENT= ColorfulTextDesign.getTextForCanceledProcess("Not Found any suitable course for the student.");
    public static final String STUDENT_COURSES_ARE_UPDATED= ColorfulTextDesign.getSuccessColorText("Not Found any suitable course for the student.");
    public static final String STUDENT_DELETE_PROCESS_CANCELED= ColorfulTextDesign.getTextForCanceledProcess("Student Delete process is Cancelled");




    public static final String COURSE_NOT_SELECTED_PROCESS_CANCELED = ColorfulTextDesign.getTextForCanceledProcess("Course is not selected. "+PROCESS_IS_CANCELLED);
    public static final String COURSE_IS_SAVED = ColorfulTextDesign.getSuccessColorText("Course is saved: ");
    public static final String COURSE_IS_UPDATED= ColorfulTextDesign.getSuccessColorText("Course is updated: ");
    public static final String COURSE_SELECTED = ColorfulTextDesign.getSuccessColorText("Selected Course: ");
    public static final String COURSE_IS_DELETED = ColorfulTextDesign.getSuccessColorText("Course is deleted");
    public static final String NOT_FOUND_ANY_SAVED_COURSE = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved Courses. Please save course first.\n"+PROCESS_IS_CANCELLED);
    public static final String COURSE_NAME_MUST_BE_UNIQUE = ColorfulTextDesign.getErrorColorText("Course name must be unique. "+PROCESS_IS_CANCELLED_FATAL);
    public static final String IS_SAVED_BEFORE = " is saved Before.";


    public static final String NOT_FOUND_ANY_SAVED_EXAM_RESULT = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved Exam Result. Please save Exam Result first. " + PROCESS_IS_CANCELLED);
    public static final String EXAM_RESULT_SAVE_PROCESS_IS_CANCELLED = ColorfulTextDesign.getTextForCanceledProcess("Exam Result Save process is cancelled. ");


    public static final String CHANGES_ARE_UPDATED = ColorfulTextDesign.getSuccessColorText("Changes are updated.");

}
