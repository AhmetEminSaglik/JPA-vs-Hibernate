package org.aes.compare.orm.exceptions;

import org.aes.compare.orm.utility.ColorfulTextDesign;

public class InvalidCourseDeleteRequestStudentEnrolled extends Exception {
    public InvalidCourseDeleteRequestStudentEnrolled(String courseName) {
        super(
                ColorfulTextDesign.getErrorColorTextWithPrefix("Course(") +
                        ColorfulTextDesign.getInfoColorText(courseName) +
                        ColorfulTextDesign.getErrorColorText(") has been enrolled by students."));
    }
}
