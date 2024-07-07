package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Student;

import java.util.List;

public interface StudentService {

    void save(Student s);

    Student findById(int id);

    List<Student> findAll();

    Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult;

    void update(Student s);

    void deleteById(int id);

    void resetTable();

}
