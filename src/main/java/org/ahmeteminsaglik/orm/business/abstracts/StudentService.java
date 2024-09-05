package org.ahmeteminsaglik.orm.business.abstracts;

import org.ahmeteminsaglik.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.ahmeteminsaglik.orm.exceptions.InvalidStudentDeleteRequestHavingExamResult;
import org.ahmeteminsaglik.orm.model.Student;

import java.util.List;

public interface StudentService {

    void save(Student s);

    Student findById(int id);

    List<Student> findAll();

    Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult;

    void update(Student s);

    void deleteById(int id) throws InvalidStudentDeleteRequestHavingExamResult;

    void resetTable();

}
