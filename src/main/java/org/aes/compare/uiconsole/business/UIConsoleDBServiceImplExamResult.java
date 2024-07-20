package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.config.ORMConfigSingleton;

public class UIConsoleDBServiceImplExamResult {
    private final ORMConfigSingleton ormConfig = new ORMConfigSingleton();


    public void save() {
        //ormConfig.getExamResultService().save(examResult);
    }


    public void findAll() {
        // return ormConfig.getExamResultService().findAll();
    }


    public void findAllByStudentId() {
        //return ormConfig.getExamResultService().findAllByStudentId(studentId);
    }


    public void findAllByStudentIdAndCourseName() {
        // return ormConfig.getExamResultService().findAllByStudentIdAndCourseName(studentId, courseName);
    }


    public void findAllByCourseName() {
        //return ormConfig.getExamResultService().findAllByCourseName(courseName);
    }


    public void findById() {
        //  return ormConfig.getExamResultService().findById(id);
    }


    public void update() {
        //  ormConfig.getExamResultService().update(examResult);
    }


    public void delete() {
        // ormConfig.getExamResultService().deleteById(id);
    }


    public void resetTable() {
        //  ormConfig.getExamResultService().resetTable();
    }
}
