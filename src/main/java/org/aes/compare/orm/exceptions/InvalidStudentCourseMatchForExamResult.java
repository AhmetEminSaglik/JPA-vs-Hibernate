package org.aes.compare.orm.exceptions;

import org.aes.compare.orm.model.ExamResult;

public class InvalidStudentCourseMatchForExamResult extends Exception {
    public InvalidStudentCourseMatchForExamResult(ExamResult examResult) {
        super("INVALID Student(id:" + examResult.getStudent().getId() + ")-Course(name:" + examResult.getCourse().getName() + ") Match");
    }

    public InvalidStudentCourseMatchForExamResult(int studentId, String courseName) {
        super("INVALID Student(id:" + studentId + ")-Course(name:" + courseName + ") Match");
    }

}
