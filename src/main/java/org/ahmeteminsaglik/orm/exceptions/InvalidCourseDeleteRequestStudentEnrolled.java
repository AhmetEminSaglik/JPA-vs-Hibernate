package org.ahmeteminsaglik.orm.exceptions;

import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;

public class InvalidCourseDeleteRequestStudentEnrolled extends Exception {
    public InvalidCourseDeleteRequestStudentEnrolled(String courseName) {
        super(
                ColorfulTextDesign.getErrorColorTextWithPrefix("Course(") +
                        ColorfulTextDesign.getInfoColorText(courseName) +
                        ColorfulTextDesign.getErrorColorText(") has been enrolled by students."));
    }
}
