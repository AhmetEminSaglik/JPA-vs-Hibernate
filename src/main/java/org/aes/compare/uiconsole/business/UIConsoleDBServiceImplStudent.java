package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Student;

import java.util.List;

public class UIConsoleDBServiceImplStudent implements StudentService {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    @Override
    public void save(Student s) {
        ormConfig.getStudentService().save(s);
    }

    @Override
    public Student findById(int id) {
        return ormConfig.getStudentService().findById(id);
    }

    @Override
    public List<Student> findAll() {
        return ormConfig.getStudentService().findAll();
    }

    @Override
    public Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult {
        return ormConfig.getStudentService().findByStudentIdWithCourseName(studentId, courseName);
    }

    @Override
    public void update(Student s) {
        ormConfig.getStudentService().update(s);
    }

    @Override
    public void deleteById(int id) {
        ormConfig.getStudentService().deleteById(id);
    }

    @Override
    public void resetTable() {
        ormConfig.getStudentService().resetTable();
    }
}
