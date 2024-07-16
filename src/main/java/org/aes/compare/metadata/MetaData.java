package org.aes.compare.metadata;

import org.aes.compare.orm.utility.ColorfulTextDesign;

public class MetaData {
    public  static  final  String SELECT_ONE_OPTION="Please type option number to run that process: ";
    public  static  final  String PROCESS_IS_CANCELLED="Process is canceled.";
    public  static  final String SWITCH_DEFAULT_INVALID_CHOICE= ColorfulTextDesign.getErrorColorTextWithPrefix("Invalid Choose try again!");
    public  static  final String EXITING_FROM_PROCESS="Exiting from process...";
    public  static  final String PROCESS_PREFIX_ADDRESS="[Address]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_STUDENT="[Student]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_COURSE="[Course]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_EXAM_RESULT="[Exam Result]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_GLOBAL="[Global]: ".toUpperCase();
    public  static  final String PROCESS_PREFIX_SETTINGS="[Settings]: ".toUpperCase();
    public  static  final String PROCESS_LIST="Process List\n";

    public static final String ADDRESS_IS_UPDATED = ColorfulTextDesign.getSuccessColorText("Address is updated: ");
    public static final String ADDRESS_IS_USING = ColorfulTextDesign.getSuccessColorText("Using address is: ");


    public static final String STUDENT_IS_UPDATED = ColorfulTextDesign.getSuccessColorText("Student is updated: ");
    public static final String STUDENT_IS_SAVED = ColorfulTextDesign.getSuccessColorText("Student is saved: ");
    public static final String STUDENT_PROCESS_CANCELLED_BECAUSE_ADDRESS_NOT_ATTACHED= ColorfulTextDesign.getErrorColorTextWithPrefix("Student can not save without Address Data. "+PROCESS_IS_CANCELLED);
    public static final String NOT_FOUND_ANY_SAVED_STUDENT = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved student. Please save student first. "+PROCESS_IS_CANCELLED);



    public static final String COURSE_NOT_SELECTED_PROCESS_CANCELED = ColorfulTextDesign.getTextForCanceledProcess("Course is not selected. "+PROCESS_IS_CANCELLED);
    public static final String COURSE_SELECTED = ColorfulTextDesign.getSuccessColorText("Selected Course: ");
    public static final String NOT_FOUND_ANY_SAVED_COURSE = ColorfulTextDesign.getTextForCanceledProcess("Has not found any saved Courses. Please save course first. "+PROCESS_IS_CANCELLED);






}
