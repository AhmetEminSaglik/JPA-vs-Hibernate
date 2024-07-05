package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;

import java.util.List;

public interface ExamResultService {

    void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult;

    List<ExamResult> findAll();

    ExamResult findById(int id);

    void update(ExamResult examResult);

    void deleteById(int id);

    void resetTable();

}
