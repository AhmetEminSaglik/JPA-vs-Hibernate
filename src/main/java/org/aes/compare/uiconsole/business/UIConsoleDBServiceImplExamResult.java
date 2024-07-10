package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;

import java.util.List;

public class UIConsoleDBServiceImplExamResult implements ExamResultService {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    @Override
    public void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult {
        ormConfig.getExamResultService().save(examResult);
    }

    @Override
    public List<ExamResult> findAll() {
        return ormConfig.getExamResultService().findAll();
    }

    @Override
    public List<ExamResult> findAllByStudentId(int studentId) {
        return ormConfig.getExamResultService().findAllByStudentId(studentId);
    }

    @Override
    public List<ExamResult> findAllByStudentIdAndCourseName(int studentId, String courseName) {
        return ormConfig.getExamResultService().findAllByStudentIdAndCourseName(studentId, courseName);
    }

    @Override
    public List<ExamResult> findAllByCourseName(String courseName) {
        return ormConfig.getExamResultService().findAllByCourseName(courseName);
    }

    @Override
    public ExamResult findById(int id) {
        return ormConfig.getExamResultService().findById(id);
    }

    @Override
    public void update(ExamResult examResult) {
        ormConfig.getExamResultService().update(examResult);
    }

    @Override
    public void deleteById(int id) {
        ormConfig.getExamResultService().deleteById(id);
    }

    @Override
    public void resetTable() {
        ormConfig.getExamResultService().resetTable();
    }
}
