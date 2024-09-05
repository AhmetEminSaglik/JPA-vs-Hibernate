package org.ahmeteminsaglik.orm.exceptions;

import org.ahmeteminsaglik.orm.model.Student;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;

public class InvalidStudentDeleteRequestHavingExamResult extends Exception {
    public InvalidStudentDeleteRequestHavingExamResult(Student student) {
        super(
                ColorfulTextDesign.getErrorColorTextWithPrefix("Student(id=") +
                        ColorfulTextDesign.getInfoColorText(Integer.toString(student.getId())) +
                        ColorfulTextDesign.getErrorColorText(") has exam result. Can not delete a student while having exam result data."));
    }
}
