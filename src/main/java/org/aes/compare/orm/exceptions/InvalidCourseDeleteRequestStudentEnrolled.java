package org.aes.compare.orm.exceptions;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public class InvalidCourseDeleteRequestStudentEnrolled extends Exception {
    public InvalidCourseDeleteRequestStudentEnrolled(String courseName) {
//        super(courseName + " course has been enrolled by students. \nTo delete this course first remove all students who take this course.");
        super(
                ColorfulTextDesign.getErrorColorTextWithPrefix("Course(") +
                        ColorfulTextDesign.getInfoColorText(courseName) +
                        ColorfulTextDesign.getErrorColorText(") has been enrolled by students."));
    }
}
