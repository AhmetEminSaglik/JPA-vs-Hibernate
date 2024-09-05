package org.ahmeteminsaglik.orm.exceptions;

import org.ahmeteminsaglik.orm.model.ExamResult;

public class InvalidStudentCourseMatchForExamResult extends Exception {
    public InvalidStudentCourseMatchForExamResult(ExamResult examResult) {
        super("INVALID Student(id:" + examResult.getStudent().getId() + ")-Course(name:" + examResult.getCourse().getName() + ") MATCH");
    }

    public InvalidStudentCourseMatchForExamResult(int studentId, String courseName) {
        super("INVALID Student(id:" + studentId + ")-Course(name:" + courseName + ") MATCH");
    }

}
