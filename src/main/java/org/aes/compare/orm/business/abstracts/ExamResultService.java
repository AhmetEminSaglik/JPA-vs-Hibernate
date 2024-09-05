package org.ahmeteminsaglik.orm.business.abstracts;

import org.ahmeteminsaglik.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.ahmeteminsaglik.orm.model.ExamResult;

import java.util.List;

public interface ExamResultService {

    void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult;

    List<ExamResult> findAll();

    List<ExamResult> findAllByStudentId(int studentId);

    List<ExamResult> findAllByStudentIdAndCourseName(int studentId, String courseName);

    List<ExamResult> findAllByCourseName(String courseName);

    ExamResult findById(int id);

    void update(ExamResult examResult);

    void deleteById(int id);

    void resetTable();

}
