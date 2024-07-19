package org.aes.compare.orm.exceptions;

public class InvalidCourseDeleteRequestStudentEnrolled extends Exception {
    public InvalidCourseDeleteRequestStudentEnrolled(String courseName) {
        super(courseName + " course has been enrolled by students. \nTo delete this course first remove all students who take this course.");
    }
}
